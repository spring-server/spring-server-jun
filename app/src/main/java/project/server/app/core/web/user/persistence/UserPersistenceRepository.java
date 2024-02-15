package project.server.app.core.web.user.persistence;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import project.server.app.common.configuration.DatabaseConfiguration;
import project.server.app.core.domain.user.User;
import project.server.app.core.domain.user.UserRepository;
import project.server.jdbc.core.exception.DataAccessException;
import static project.server.jdbc.core.jdbc.JdbcHelper.insert;
import static project.server.jdbc.core.jdbc.JdbcHelper.selectAll;
import static project.server.jdbc.core.jdbc.JdbcHelper.selectBy;
import static project.server.jdbc.core.jdbc.JdbcHelper.truncate;
import static project.server.jdbc.core.jdbc.JdbcHelper.update;
import project.server.jdbc.core.jdbc.JdbcTemplate;
import project.server.jdbc.core.jdbc.RowMapper;
import project.server.mvc.springframework.annotation.Repository;

@Repository
public class UserPersistenceRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper;

    private UserPersistenceRepository(
        DatabaseConfiguration dbConfig,
        RowMapper<User> rowMapper
    ) throws IOException {
        this.jdbcTemplate = dbConfig.jdbcTemplate();
        this.rowMapper = rowMapper;
    }

    @Override
    public Long save(User user) {
        String sql = insert();
        return jdbcTemplate.queryForObject(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(sql, RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setTimestamp(3, Timestamp.valueOf(user.getCreatedAt()));
            pstmt.setTimestamp(4, (user.getLastModifiedAt() != null) ? Timestamp.valueOf(user.getLastModifiedAt()) : null);
            pstmt.setObject(5, user.getDeleted().toString());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new DataAccessException();
                }
            }
        });
    }

    @Override
    public Optional<User> findById(Long userId) {
        String sql = selectBy(User.class);
        return jdbcTemplate.queryForObject(sql, pstmt ->
                pstmt.setLong(1, userId),
            rs -> rs.next() ? ofNullable(rowMapper.mapRow(rs)) : empty()
        );
    }

    @Override
    public boolean existsByName(String username) {
        String sql = selectBy(User.class, "username");
        return jdbcTemplate.queryForObject(sql, pstmt ->
                pstmt.setString(1, username),
            rs -> rs.next() && rs.getInt(1) > 0
        );
    }

    @Override
    public Optional<User> findByUsernameAndPassword(
        String username,
        String password
    ) {
        String sql = selectBy(User.class, "username", "password");
        return jdbcTemplate.queryForObject(sql, pstmt -> {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
            }, rs -> rs.next() ? ofNullable(rowMapper.mapRow(rs)) : empty()
        );
    }

    @Override
    public void delete(User user) {
        String sql = update(User.class, "deleted");
        jdbcTemplate.queryForObject(sql, pstmt -> {
            pstmt.setLong(1, user.getId());
            pstmt.executeUpdate();
            return null;
        });
    }

    @Override
    public List<User> findAll() {
        String sql = selectAll(User.class);
        return jdbcTemplate.queryForList(sql, rowMapper);
    }

    @Override
    public void clear() {
        String sql = truncate(User.class);
        jdbcTemplate.execute(sql);
    }
}
