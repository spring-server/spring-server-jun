package project.server.app.core.web.user.presentation;

import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.springframework.annotation.Controller;
import project.server.mvc.springframework.annotation.GetMapping;
import project.server.mvc.springframework.web.servlet.Handler;
import project.server.mvc.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController implements Handler {

    @Override
    @GetMapping(path = "/")
    public ModelAndView process(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        return new ModelAndView("index.html");
    }
}
