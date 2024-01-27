package project.server.app.core.web.user.exception;

import project.server.app.common.codeandmessage.ErrorCodeAndMessage;
import project.server.app.common.exception.BusinessException;
import static project.server.app.core.web.user.exception.UserErrorCodeAndMessage.ALREADY_SAVED_USER;

public class AlreadyRegisteredUserException extends BusinessException {

    private final ErrorCodeAndMessage codeAndMessage;

    public AlreadyRegisteredUserException() {
        super(ALREADY_SAVED_USER);
        this.codeAndMessage = ALREADY_SAVED_USER;
    }

    @Override
    public ErrorCodeAndMessage getCodeAndMessage() {
        return codeAndMessage;
    }
}
