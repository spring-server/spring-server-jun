package project.server.mvc.servlet.http;

public enum StatusCode {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String statusText;

    StatusCode(
        int code,
        String statusText
    ) {
        this.code = code;
        this.statusText = statusText;
    }

    public int getCode() {
        return this.code;
    }

    public String getStatusText() {
        return this.statusText;
    }
}
