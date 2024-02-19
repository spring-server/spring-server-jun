package project.server.app.core.web.user.presentation;

import lombok.extern.slf4j.Slf4j;
import project.server.app.common.login.LoginUser;
import project.server.app.core.web.user.application.UserLoginUseCase;
import project.server.app.core.web.user.application.UserUpdateUseCase;
import project.server.app.core.web.user.presentation.validator.UserValidator;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import static project.server.mvc.servlet.http.HttpStatus.OK;
import project.server.mvc.springframework.annotation.Controller;
import project.server.mvc.springframework.web.servlet.Handler;
import project.server.mvc.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class UserInfoUpdateController implements Handler {

    private final UserValidator validator;
    private final UserLoginUseCase loginUseCase;
    private final UserUpdateUseCase userUpdateUseCase;

    public UserInfoUpdateController(
        UserValidator validator,
        UserLoginUseCase loginUseCase,
        UserUpdateUseCase userUpdateUseCase
    ) {
        this.validator = validator;
        this.loginUseCase = loginUseCase;
        this.userUpdateUseCase = userUpdateUseCase;
    }

    @Override
    public ModelAndView process(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        LoginUser loginUser = (LoginUser) request.getAttribute("loginUser");
        userUpdateUseCase.update(loginUser, (String) request.getAttribute("password"));

        response.setStatus(OK);
        return null;
    }
}
