package project.server.app.common.exception;

import project.server.app.common.codeandmessage.ErrorCodeAndMessage;
import project.server.mvc.servlet.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final ErrorCodeAndMessage codeAndMessage;

    public BusinessException(ErrorCodeAndMessage errorCodeAndMessage) {
        super(errorCodeAndMessage.getErrorMessage());
        this.codeAndMessage = errorCodeAndMessage;
    }

    public ErrorCodeAndMessage getCodeAndMessage() {
        return codeAndMessage;
    }

    @Override
    public String toString() {
        HttpStatus status = codeAndMessage.getStatus();
        return String.format(
            "{\"code\":%d, \"message\":\"%s\"}",
            status.getStatusCode(),
            codeAndMessage.getErrorMessage()
        );
    }
}
