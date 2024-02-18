package project.server.app.common.exception;

import project.server.app.common.codeandmessage.failure.ErrorCodeAndMessages;
import project.server.mvc.servlet.http.HttpStatus;

public class InvalidParameterException extends RuntimeException {

    private final ErrorCodeAndMessages errorCodeAndMessages;
    private final Object[] args;

    public InvalidParameterException(Object... args) {
        this.errorCodeAndMessages = ErrorCodeAndMessages.INVALID_PARAMETER;
        this.args = args.clone();
    }

    public ErrorCodeAndMessages getErrorCodeAndMessages() {
        return errorCodeAndMessages;
    }

    public Object getArgs() {
        return args;
    }

    @Override
    public String toString() {
        HttpStatus status = errorCodeAndMessages.getStatus();
        return String.format(
            "{\"code\":%d, \"message\":\"%s\"}",
            status.getStatusCode(),
            errorCodeAndMessages.getErrorMessage()
        );
    }
}
