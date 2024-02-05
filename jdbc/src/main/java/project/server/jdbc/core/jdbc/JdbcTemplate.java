package project.server.jdbc.core.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import static java.lang.reflect.Modifier.isStatic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import static project.server.jdbc.core.jdbc.JdbcHelper.convertCamelToSnake;
import static project.server.jdbc.core.jdbc.JdbcHelper.convertValueToFieldType;
import static project.server.jdbc.core.jdbc.JdbcHelper.createInsertSQL;
import static project.server.jdbc.core.jdbc.JdbcHelper.getValue;

@Slf4j
public class JdbcTemplate<T> extends JdbcAccessor implements JdbcOperations {

    public JdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public T save(T entity) throws IllegalAccessException, SQLException {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String sql = createInsertSQL(clazz, fields);

        try (
            Connection conn = getDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            int index = 1;
            for (Field field : fields) {
                if (isStatic(field.getModifiers()) || field.isSynthetic()) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(entity);
                pstmt.setString(index++, getValue(value));
            }
            pstmt.executeUpdate();
        }
        return entity;
    }

    public T findById(
        Class<T> clazz,
        Long id
    ) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String sql = new StringBuilder("SELECT * FROM ")
            .append(convertCamelToSnake(clazz.getSimpleName()))
            .append(" WHERE id = ?")
            .toString();

        try (
            Connection conn = getDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    T entity = clazz.getDeclaredConstructor().newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (isStatic(field.getModifiers()) || field.isSynthetic()) {
                            continue;
                        }
                        field.setAccessible(true);
                        Object value = rs.getObject(convertCamelToSnake(field.getName()));
                        if (value != null) {
                            Object convertedValue = convertValueToFieldType(rs, field);
                            field.set(entity, convertedValue);
                        }
                    }
                    return entity;
                } else {
                    return null;
                }
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean existsByField(
        Class<T> clazz,
        String fieldName,
        String fieldValue
    ) throws SQLException {
        String tableName = convertCamelToSnake(clazz.getSimpleName());
        String sql = new StringBuilder("SELECT COUNT(1) FROM ")
            .append(tableName)
            .append(" WHERE ")
            .append(convertCamelToSnake(fieldName))
            .append(" = ?")
            .toString();

        try (
            Connection conn = getDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, fieldValue);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                } else {
                    return false;
                }
            }
        }
    }

    public Optional<T> findByUsernameAndPassword(
        Class<T> clazz,
        String username,
        String password
    ) throws SQLException {
        String sql = new StringBuilder("SELECT * FROM ")
            .append(convertCamelToSnake(clazz.getSimpleName()))
            .append(" WHERE username = ? AND password = ?")
            .toString();

        try (
            Connection conn = getDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    T entity = clazz.getDeclaredConstructor().newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (isStatic(field.getModifiers()) || field.isSynthetic()) {
                            continue;
                        }
                        field.setAccessible(true);
                        Object convertedValue = convertValueToFieldType(rs, field);
                        field.set(entity, convertedValue);
                    }
                    return Optional.of(entity);
                } else {
                    return Optional.empty();
                }
            } catch (Exception exception) {
                throw new SQLException("Entity instantiation error", exception);
            }
        }
    }

    public List<T> findAll(Class<T> clazz) throws SQLException {
        String tableName = convertCamelToSnake(clazz.getSimpleName());
        String sql = new StringBuilder("SELECT * FROM ")
            .append(tableName)
            .toString();

        try (
            Connection conn = getDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {
            List<T> entities = new ArrayList<>();
            while (rs.next()) {
                try {
                    T entity = clazz.getDeclaredConstructor().newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (isStatic(field.getModifiers()) || field.isSynthetic()) {
                            continue;
                        }
                        field.setAccessible(true);
                        Object value = rs.getObject(convertCamelToSnake(field.getName()));
                        if (value != null) {
                            Object convertedValue = convertValueToFieldType(rs, field);
                            field.set(entity, convertedValue);
                        }
                    }
                    entities.add(entity);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new SQLException("Error instantiating entity", e);
                }
            }
            return entities;
        }
    }

    public void truncate(Class<T> clazz) throws SQLException {
        String tableName = convertCamelToSnake(clazz.getSimpleName());
        String sql = new StringBuilder("TRUNCATE TABLE ")
            .append(tableName)
            .toString();

        try (
            Connection conn = getDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.execute();
            log.info("Table: {}", tableName);
        } catch (SQLException exception) {
            log.error("Exception: {}", tableName, exception);
            throw exception;
        }
    }

    @Override
    public void afterPropertiesSet() {
        log.info("Repository initialized.");
    }
}
