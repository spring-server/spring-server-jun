package project.server.mvc.servlet;

import project.server.mvc.servlet.http.Cookie;
import project.server.mvc.servlet.http.HttpStatus;

public interface HttpServletResponse extends ServletResponse {
    String getStatusAsString();

    void setStatus(HttpStatus status);

    void addCookie(Cookie cookie);

    String getCookiesAsString();
}
