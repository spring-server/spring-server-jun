package project.server.app.core.web.user.exception;

import project.server.app.common.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(UserErrorCodeAndMessage.NOT_FOUND);
    }
}
