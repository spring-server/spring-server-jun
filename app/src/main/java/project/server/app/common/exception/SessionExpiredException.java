package project.server.app.common.exception;

import static project.server.app.common.codeandmessage.failure.ErrorCodeAndMessages.INVALID_SESSION;

public class SessionExpiredException extends RuntimeException {

    public SessionExpiredException() {
        super(INVALID_SESSION.getErrorMessage());
    }
}
