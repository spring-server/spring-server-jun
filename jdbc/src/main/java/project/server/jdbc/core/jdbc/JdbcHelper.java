package project.server.jdbc.core.jdbc;

import java.util.Arrays;
import static java.util.stream.Collectors.joining;

public final class JdbcHelper {

    private static final String ENGLISH_ALPHA = "([a-z])([A-Z]+)";
    private static final String REPLACEMENT_A_TO_B = "$1_$2";

    private JdbcHelper() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    public static String insert() {
        return "INSERT INTO user "
            + "(username, password, created_at, last_modified_at, deleted) "
            + "VALUES (?, ?, ?, ?, ?)";
    }

    public static String selectBy(Class<?> clazz) {
        return "SELECT * FROM "
            + convertCamelToSnake(clazz.getSimpleName())
            + " WHERE id = ? AND deleted = 'FALSE'";
    }

    public static String selectBy(
        Class<?> clazz,
        String... fieldNames
    ) {
        String tableName = convertCamelToSnake(clazz.getSimpleName());
        String whereClause = Arrays.stream(fieldNames)
            .map(fieldName -> convertCamelToSnake(fieldName) + " = ?")
            .collect(joining(" AND "));
        return "SELECT * FROM " + tableName + " WHERE " + whereClause;
    }

    public static String update(
        Class<?> clazz,
        String fieldName
    ) {
        return "UPDATE "
            + convertCamelToSnake(clazz.getSimpleName())
            + " SET "
            + convertCamelToSnake(fieldName)
            + " = 'TRUE' WHERE id = ?";
    }

    public static String selectAll(Class<?> clazz) {
        return "SELECT * FROM "
            + convertCamelToSnake(clazz.getSimpleName());
    }

    public static String truncate(Class<?> clazz) {
        return "TRUNCATE TABLE "
            + convertCamelToSnake(clazz.getSimpleName());
    }

    public static String convertCamelToSnake(String value) {
        return value.replaceAll(ENGLISH_ALPHA, REPLACEMENT_A_TO_B)
            .toLowerCase();
    }
}
