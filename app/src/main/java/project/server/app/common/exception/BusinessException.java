package project.server.app.common.exception;

import project.server.app.common.codeandmessage.ErrorCodeAndMessage;

public class BusinessException extends RuntimeException {

    private final ErrorCodeAndMessage codeAndMessage;

    public BusinessException(ErrorCodeAndMessage errorCodeAndMessage) {
        super(errorCodeAndMessage.getErrorMessage());
        this.codeAndMessage = errorCodeAndMessage;
    }

    public ErrorCodeAndMessage getCodeAndMessage() {
        return codeAndMessage;
    }
}
