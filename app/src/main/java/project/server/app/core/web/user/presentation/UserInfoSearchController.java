package project.server.app.core.web.user.presentation;

import lombok.extern.slf4j.Slf4j;
import project.server.app.common.login.LoginUser;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserSearchUseCase;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import static project.server.mvc.servlet.http.HttpStatus.OK;
import project.server.mvc.springframework.annotation.Controller;
import project.server.mvc.springframework.annotation.GetMapping;
import project.server.mvc.springframework.annotation.RequestMapping;
import project.server.mvc.springframework.ui.ModelMap;
import project.server.mvc.springframework.web.servlet.Handler;
import project.server.mvc.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserInfoSearchController implements Handler {

    private final UserSearchUseCase userSearchUseCase;

    public UserInfoSearchController(UserSearchUseCase userSearchUseCase) {
        this.userSearchUseCase = userSearchUseCase;
    }

    @Override
    @GetMapping(path = "/my-info")
    public ModelAndView process(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        LoginUser loginUser = (LoginUser) request.getAttribute("loginUser");

        User findUser = userSearchUseCase.findById(loginUser.getUserId());
        ModelMap modelMap = createModelMap(findUser);
        response.setStatus(OK);
        return new ModelAndView("/my-info.html", modelMap);
    }

    private ModelMap createModelMap(User findUser) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("username", findUser.getUsername());
        modelMap.put("password", findUser.getPassword());
        return modelMap;
    }
}
