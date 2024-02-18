package project.server.app.core.web.user.presentation;

import lombok.extern.slf4j.Slf4j;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.presentation.validator.UserValidator;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import static project.server.mvc.servlet.http.HttpStatus.OK;
import project.server.mvc.springframework.annotation.Controller;
import project.server.mvc.springframework.annotation.RequestMapping;
import project.server.mvc.springframework.web.servlet.Handler;
import project.server.mvc.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/users")
public class SignUpController implements Handler {

    private final UserValidator validator;
    private final UserSaveUseCase userSaveUseCase;

    public SignUpController(
        UserValidator validator,
        UserSaveUseCase userSaveUseCase
    ) {
        this.validator = validator;
        this.userSaveUseCase = userSaveUseCase;
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
        userSaveUseCase.save(username, password);

        response.setStatus(OK);
        return new ModelAndView("redirect:/index.html");
    }
}
