package project.server.app.core.web.user.presentation;

import lombok.extern.slf4j.Slf4j;
import project.server.app.common.login.Session;
import project.server.app.core.web.user.application.LoginUseCase;
import project.server.app.core.web.user.presentation.validator.UserValidator;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.springframework.annotation.Controller;
import project.server.mvc.springframework.web.servlet.Handler;
import project.server.mvc.springframework.web.servlet.ModelAndView;
import project.server.mvc.servlet.http.Cookie;
import static project.server.mvc.servlet.http.HttpStatus.OK;

@Slf4j
@Controller
public class LoginController implements Handler {

    private final UserValidator validator;
    private final LoginUseCase loginUseCase;

    public LoginController(
        UserValidator validator,
        LoginUseCase loginUseCase
    ) {
        this.validator = validator;
        this.loginUseCase = loginUseCase;
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
        Session session = loginUseCase.login(username, password);

        setResponse(response, session);
        return new ModelAndView("redirect:/index.html");
    }

    private void setResponse(
        HttpServletResponse response,
        Session session
    ) {
        Cookie cookie = new Cookie(session.getUserId(), session.getSessionId());
        response.addCookie(cookie);
        response.setStatus(OK);
    }
}
