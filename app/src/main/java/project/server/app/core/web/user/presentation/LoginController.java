package project.server.app.core.web.user.presentation;

import lombok.extern.slf4j.Slf4j;
import project.server.app.common.login.Session;
import project.server.app.core.web.user.application.UserLoginUseCase;
import project.server.app.core.web.user.presentation.validator.UserValidator;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.http.Cookie;
import static project.server.mvc.servlet.http.HttpStatus.MOVE_PERMANENTLY;
import project.server.mvc.springframework.annotation.Controller;
import project.server.mvc.springframework.web.servlet.Handler;
import project.server.mvc.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class LoginController implements Handler {

    private static final String MAX_AGE = "; Max-Age=900";

    private final UserValidator validator;
    private final UserLoginUseCase userLoginUseCase;

    public LoginController(
        UserValidator validator,
        UserLoginUseCase userLoginUseCase
    ) {
        this.validator = validator;
        this.userLoginUseCase = userLoginUseCase;
    }

    @Override
    public ModelAndView process(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String username = request.getAttribute("username");
        String password = request.getAttribute("password");
        log.info("username: {}, password: {}", username, password);

        validator.validateLoginInfo(username, password);
        Session session = userLoginUseCase.login(username, password);

        setResponse(response, session);
        return new ModelAndView("redirect:/index.html");
    }

    private void setResponse(
        HttpServletResponse response,
        Session session
    ) {
        Cookie cookie = new Cookie("sessionId", session.getUserIdAsString() + MAX_AGE);
        response.addCookie(cookie);
        response.setStatus(MOVE_PERMANENTLY);
    }
}
