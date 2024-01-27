package project.server.app.core.web.user.presentation;

import lombok.extern.slf4j.Slf4j;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.mvc.springframework.annotation.Controller;
import project.server.mvc.springframework.annotation.RequestMapping;
import project.server.mvc.springframework.web.servlet.Handler;
import project.server.mvc.springframework.web.servlet.ModelAndView;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/users")
public class SignUpController implements Handler {

    private final UserSaveUseCase userSaveUseCase;

    public SignUpController(UserSaveUseCase userSaveUseCase) {
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
        userSaveUseCase.save(new User(username, password));
        return new ModelAndView("redirect:/index.html");
    }
}
