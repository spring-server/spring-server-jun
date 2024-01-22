package project.server.mvc.servlet.http;

public enum HttpStatus {
    OK("OK", 200),
    BAD_REQUEST("BAD_REQUEST", 400),
    NOT_FOUND("NOT_FOUND", 404);

    private final String status;
    private final int statusCode;

    HttpStatus(
        String status,
        int statusCode
    ) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
