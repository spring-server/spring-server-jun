package project.server.jdbc.core.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ofPattern;
import java.util.Arrays;
import java.util.List;

public final class JdbcHelper {

    private static final String INSERT_START_PREFIX = "INSERT INTO ";
    private static final String START_BRACKET = " (";
    private static final String FIELD_DELIMITER = ", ";
    private static final String PARAMETER_DELIMITER = "?, ";
    private static final String VALUE_DELIMITER = ") VALUES (";
    private static final String END_BRACKET = ")";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String ENGLISH_ALPHA = "([a-z])([A-Z]+)";
    private static final String REPLACEMENT_A_TO_B = "$1_$2";

    private static final List<String> ddl = Arrays.stream(Ddl.values())
        .map(Enum::name)
        .toList();

    private JdbcHelper() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    public static String createInsertSQL(
        Class<?> clazz,
        Field[] fields
    ) {
        StringBuilder sql = new StringBuilder(INSERT_START_PREFIX);
        sql.append(convertCamelToSnake(clazz.getSimpleName()));
        sql.append(START_BRACKET);

        StringBuilder placeholders = new StringBuilder();
        for (Field field : fields) {
            sql.append(convertCamelToSnake(field.getName()))
                .append(FIELD_DELIMITER);
            placeholders.append(PARAMETER_DELIMITER);
        }

        removeLastComma(sql, placeholders);
        return sql.append(VALUE_DELIMITER)
            .append(placeholders)
            .append(END_BRACKET)
            .toString();
    }

    private static void removeLastComma(
        StringBuilder sql,
        StringBuilder placeholders
    ) {
        sql.setLength(sql.length() - 2);
        placeholders.setLength(placeholders.length() - 2);
    }

    public static Object convertValueToFieldType(
        ResultSet resultSet,
        Field field
    ) throws SQLException {
        Class<?> type = field.getType();
        String column = convertCamelToSnake(field.getName());
        Object value = resultSet.getObject(column);

        if (value == null) {
            return null;
        }

        if (type.equals(Long.class) || type.equals(long.class)) {
            return resultSet.getLong(column);
        } else if (type.equals(Integer.class) || type.equals(int.class)) {
            return resultSet.getInt(column);
        } else if (type.equals(String.class)) {
            return value.toString();
        } else if (type.equals(LocalDateTime.class)) {
            DateTimeFormatter formatter = ofPattern(DATE_TIME_FORMAT);
            String dateTimeStr = resultSet.getString(column);
            return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, formatter) : null;
        } else if (type.isEnum()) {
            @SuppressWarnings("unchecked")
            Class<? extends Enum> enumType = (Class<? extends Enum>) type;
            @SuppressWarnings("unchecked")
            Enum<?> result = Enum.valueOf(enumType, value.toString().toUpperCase());
            return result;
        } else {
            try {
                return type.getConstructor(String.class).newInstance(value.toString());
            } catch (ReflectiveOperationException exception) {
                throw new RuntimeException("Converted exception: {}", exception);
            }
        }
    }

    public static String convertCamelToSnake(String value) {
        return value.replaceAll(ENGLISH_ALPHA, REPLACEMENT_A_TO_B)
            .toLowerCase();
    }

    public static String getValue(Object value) {
        return value != null ? value.toString() : null;
    }

    public static boolean containsDdl(String fieldValue) {
        return ddl.contains(fieldValue);
    }
}
