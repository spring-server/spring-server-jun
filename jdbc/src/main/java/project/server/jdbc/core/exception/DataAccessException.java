package project.server.jdbc.core.exception;

public class DataAccessException extends RuntimeException {

    private static final String INTERNAL_SERVER_ERROR = "서버 내부 오류입니다.";

    public DataAccessException() {
        super(INTERNAL_SERVER_ERROR);
    }

    @Override
    public String toString() {
        return String.format(
            "{\"code\":%d, \"message\":\"%s\"}",
            500,
            INTERNAL_SERVER_ERROR
        );
    }
}
