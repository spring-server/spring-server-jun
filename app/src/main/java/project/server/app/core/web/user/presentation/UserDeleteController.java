package project.server.app.core.web.user.presentation;

import lombok.extern.slf4j.Slf4j;
import static project.server.app.common.utils.HeaderUtils.getSessionId;
import project.server.app.common.login.LoginUser;
import project.server.app.common.login.Session;
import project.server.app.core.web.user.application.UserDeleteUseCase;
import project.server.app.core.web.user.application.UserLoginUseCase;
import project.server.app.core.web.user.presentation.validator.UserValidator;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import static project.server.mvc.servlet.http.HttpStatus.NO_CONTENT;
import project.server.mvc.springframework.annotation.Controller;
import project.server.mvc.springframework.web.servlet.Handler;
import project.server.mvc.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class UserDeleteController implements Handler {

    private final UserValidator validator;
    private final UserLoginUseCase loginUseCase;
    private final UserDeleteUseCase userDeleteUseCase;

    public UserDeleteController(
        UserValidator validator,
        UserLoginUseCase loginUseCase,
        UserDeleteUseCase userDeleteUseCase
    ) {
        this.validator = validator;
        this.loginUseCase = loginUseCase;
        this.userDeleteUseCase = userDeleteUseCase;
    }

    @Override
    public ModelAndView process(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        Long sessionId = getSessionId(request.getCookies());
        validator.validateSessionId(sessionId, response);

        Session findSession = loginUseCase.findSessionById(sessionId);
        log.info("Session:{}", findSession);
        LoginUser loginUser = new LoginUser(findSession);

        userDeleteUseCase.delete(loginUser);
        response.setStatus(NO_CONTENT);
        return new ModelAndView("redirect/index.html");
    }
}
