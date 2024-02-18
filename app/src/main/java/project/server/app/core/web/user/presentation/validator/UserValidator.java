package project.server.app.core.web.user.presentation.validator;

import lombok.extern.slf4j.Slf4j;
import project.server.app.common.exception.InvalidParameterException;
import project.server.app.common.exception.UnAuthorizedException;
import project.server.app.common.login.Session;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.http.Cookie;
import static project.server.mvc.servlet.http.HttpStatus.UN_AUTHORIZED;
import project.server.mvc.springframework.annotation.Component;

@Slf4j
@Component
public class UserValidator {

    private static final String CACHE_CONTROL = "cache-control";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String SESSION_ID = "sessionId";
    private static final String EMPTY_STRING = "";
    private static final String MAX_AGE = "max-age=0";

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

    public void validateSessionId(
        Long userId,
        HttpServletResponse response
    ) {
        if (userId == null) {
            setInvalidSession(response);
            log.error("InvalidSession. UserId: {}", userId);
            throw new UnAuthorizedException();
        }
    }

    public void validateSession(
        Session session,
        HttpServletResponse response
    ) {
        if (session == null) {
            setInvalidSession(response);
            log.error("InvalidSession session. Session is null.");
            throw new UnAuthorizedException();
        }
    }

    private void setInvalidSession(HttpServletResponse response) {
        Cookie cookie = new Cookie(SESSION_ID, EMPTY_STRING + COOKIE_DELIMITER + MAX_AGE);
        response.addCookie(cookie);
        response.setHeader(CACHE_CONTROL, MAX_AGE);
        response.setStatus(UN_AUTHORIZED);
    }
}
