package project.server.mvc.springframework.web.servlet;

import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

public interface Handler {
    ModelAndView process(HttpServletRequest request, HttpServletResponse response);
}
