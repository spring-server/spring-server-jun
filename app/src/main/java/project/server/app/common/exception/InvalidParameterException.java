package project.server.app.common.exception;

import project.server.app.common.codeandmessage.failure.ErrorCodeAndMessages;

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
}
