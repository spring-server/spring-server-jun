package project.server.mvc.servlet;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import project.server.mvc.servlet.http.Cookie;
import project.server.mvc.servlet.http.HttpStatus;

public interface HttpServletResponse extends ServletResponse {
    String getHttpHeaderLine();

    HttpStatus getStatus();

    void setStatus(HttpStatus status);

    void addCookie(Cookie cookie);

    void setHeader(String key, String value);

    void setBody(String body);

    void write(String data) throws IOException;

    void write(byte[] data) throws IOException;
}
