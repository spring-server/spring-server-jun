package project.server.mvc.springframework.web.servlet;

import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

public interface View {
    void render(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
