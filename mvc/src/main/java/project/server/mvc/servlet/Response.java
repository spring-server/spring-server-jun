package project.server.mvc.servlet;

import java.io.OutputStream;
import project.server.mvc.servlet.http.Cookie;
import project.server.mvc.servlet.http.HttpHeaders;
import project.server.mvc.servlet.http.HttpStatus;

public class Response implements HttpServletResponse {

    private HttpStatus status;
    private final HttpHeaders headers;
    private final OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.status = HttpStatus.OK;
        this.headers = new HttpHeaders();
        this.outputStream = outputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public String getStatusAsString() {
        return status.getStatus();
    }

    @Override
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public void addCookie(Cookie cookie) {
        this.headers.addCookie(cookie);
    }

    @Override
    public String getCookiesAsString() {
        return headers.getCookiesAsString();
    }
}
