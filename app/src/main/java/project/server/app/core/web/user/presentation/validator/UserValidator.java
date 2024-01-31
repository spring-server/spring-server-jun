package project.server.app.core.web.user.presentation.validator;

import project.server.app.common.exception.InvalidParameterException;
import project.server.app.common.exception.UnAuthorizedException;
import project.server.mvc.springframework.annotation.Component;

@Component
public class UserValidator {

    public void validateSignUpInfo(
        String username,
        String password
    ) {
        if (username == null || password == null) {
            throw new InvalidParameterException(username, password);
        }
        if (username.isBlank() || password.isBlank()) {
            throw new InvalidParameterException(username, password);
        }
    }

    public void validateLoginInfo(
        String username,
        String password
    ) {
        if (username == null || password == null) {
            throw new InvalidParameterException(username, password);
        }
        if (username.isBlank() || password.isBlank()) {
            throw new InvalidParameterException(username, password);
        }
    }

    public void validateSessionId(Long userId) {
        if (userId == null) {
            throw new UnAuthorizedException();
        }
    }
}
