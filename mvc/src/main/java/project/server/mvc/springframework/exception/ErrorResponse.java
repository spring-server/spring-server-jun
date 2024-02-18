package project.server.mvc.springframework.exception;

public class ErrorResponse {

    private int code;
    private String message;

    private ErrorResponse() {
    }

    public ErrorResponse(
        int code,
        String message
    ) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("code:%s, message:%s", code, message);
    }
}
