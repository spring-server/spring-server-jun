package project.server.app.core.web.user.exception;

import project.server.app.common.exception.BusinessException;

public class DuplicatedUsernameException extends BusinessException {

    public DuplicatedUsernameException() {
        super(UserErrorCodeAndMessage.DUPLICATED_USERNAME);
    }
}
