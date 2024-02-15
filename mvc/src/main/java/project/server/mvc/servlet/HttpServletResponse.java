package project.server.mvc.servlet;

import java.nio.channels.SocketChannel;
import project.server.mvc.servlet.http.Cookie;
import project.server.mvc.servlet.http.HttpStatus;

public interface HttpServletResponse extends ServletResponse {
    String getHttpHeaderLine();

    SocketChannel getSocketChannel();

    HttpStatus getStatus();

    void setStatus(HttpStatus status);

    void addCookie(Cookie cookie);

    void setHeader(String key, String value);

    void setBody(String body);
}
