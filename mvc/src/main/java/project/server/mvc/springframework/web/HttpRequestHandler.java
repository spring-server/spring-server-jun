package project.server.mvc.springframework.web;

import java.io.IOException;
import project.server.mvc.servlet.ServletException;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

@FunctionalInterface
public interface HttpRequestHandler {
    void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
