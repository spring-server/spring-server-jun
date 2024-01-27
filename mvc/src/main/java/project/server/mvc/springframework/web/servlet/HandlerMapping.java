package project.server.mvc.springframework.web.servlet;

import project.server.mvc.servlet.HttpServletRequest;

public interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
