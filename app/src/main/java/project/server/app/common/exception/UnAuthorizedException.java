package project.server.app.common.exception;

import static project.server.app.common.codeandmessage.failure.ErrorCodeAndMessages.UN_AUTHORIZED;

public class UnAuthorizedException extends BusinessException {

    public UnAuthorizedException() {
        super(UN_AUTHORIZED);
    }
}
