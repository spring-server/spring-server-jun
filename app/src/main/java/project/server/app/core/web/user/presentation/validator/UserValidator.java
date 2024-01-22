package project.server.app.core.web.user.presentation.validator;

import project.server.app.common.exception.InvalidParameterException;
import project.server.mvc.springframework.annotation.Component;

@Component
public class UserValidator {

    public void validateSignUp(
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
}