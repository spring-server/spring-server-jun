package project.server.mvc.servlet.http;

import java.util.Arrays;
import java.util.function.Predicate;

public enum HttpStatus {
    OK("200 OK", 200),
    NO_CONTENT("204 No Content", 204),
    MOVE_PERMANENTLY("301 Moved Permanently", 301),
    BAD_REQUEST("400 Bad Request", 400),
    NOT_FOUND("NOT_FOUND", 404),
    UN_AUTHORIZED("401 Unauthorized", 401),
    INTERNAL_SERVER_ERROR("500 Internal Server Error", 500);

    private final String status;
    private final int statusCode;

    HttpStatus(
        String status,
        int statusCode
    ) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public static HttpStatus findByCode(int code) {
        return Arrays.stream(values())
            .filter(equals(code))
            .findAny()
            .orElseThrow(IllegalStateException::new);
    }

    private static Predicate<HttpStatus> equals(int code) {
        return statusCode -> statusCode.getStatusCode() == code;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
