package project.server.mvc.servlet;

import java.nio.channels.SocketChannel;
import project.server.mvc.servlet.http.Cookie;
import project.server.mvc.servlet.http.HttpHeaders;
import project.server.mvc.servlet.http.HttpStatus;
import project.server.mvc.servlet.http.ResponseBody;
import project.server.mvc.servlet.http.StatusLine;

public class Response implements HttpServletResponse {

    private final StatusLine statusLine;
    private final HttpHeaders headers;
    private final ResponseBody responseBody;
    private final SocketChannel socketChannel;

    public Response(SocketChannel socketChannel) {
        this.statusLine = new StatusLine();
        this.socketChannel = socketChannel;
        this.headers = new HttpHeaders();
        this.responseBody = new ResponseBody();
    }

    public Response() {
        this(null);
    }

    @Override
    public String getHttpHeaderLine() {
        return String.format("%s%s", statusLine, headers);
    }

    @Override
    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    @Override
    public HttpStatus getStatus() {
        return statusLine.getHttpStatus();
    }

    @Override
    public void setStatus(HttpStatus status) {
        statusLine.setStatus(status);
    }

    @Override
    public void addCookie(Cookie cookie) {
        this.headers.addCookie(cookie);
    }

    @Override
    public void setHeader(
        String key,
        String value
    ) {
        headers.addHeader(key, value);
    }

    @Override
    public void setBody(String body) {
        this.responseBody.setBody(body);
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", statusLine, headers, responseBody);
    }
}
