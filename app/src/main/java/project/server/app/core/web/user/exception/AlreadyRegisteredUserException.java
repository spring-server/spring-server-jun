package project.server.app.core.web.user.exception;

import project.server.app.common.codeandmessage.ErrorCodeAndMessage;
import project.server.app.common.exception.BusinessException;

public class AlreadyRegisteredUserException extends BusinessException {

    private final ErrorCodeAndMessage codeAndMessage;

    public AlreadyRegisteredUserException(ErrorCodeAndMessage codeAndMessage) {
        super(codeAndMessage);
        this.codeAndMessage = codeAndMessage;
    }

    @Override
    public ErrorCodeAndMessage getCodeAndMessage() {
        return codeAndMessage;
    }
}
