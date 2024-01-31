package project.server.mvc.servlet;

import java.io.OutputStream;
import java.nio.channels.SocketChannel;
import project.server.mvc.servlet.http.Cookie;
import project.server.mvc.servlet.http.HttpHeaders;
import project.server.mvc.servlet.http.HttpStatus;
import static project.server.mvc.servlet.http.HttpStatus.OK;

public class Response implements HttpServletResponse {

    private HttpStatus status;
    private final HttpHeaders headers;
    private final SocketChannel socketChannel;
    private final OutputStream outputStream;

    public Response(
        SocketChannel socketChannel,
        OutputStream outputStream
    ) {
        this.socketChannel = socketChannel;
        this.status = OK;
        this.headers = new HttpHeaders();
        this.outputStream = outputStream;
    }

    public Response(OutputStream outputStream) {
        this(null, outputStream);
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

    @Override
    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
