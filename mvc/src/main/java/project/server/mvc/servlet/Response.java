package project.server.mvc.servlet;

import java.io.OutputStream;
import project.server.mvc.servlet.http.HttpStatus;

public class Response implements HttpServletResponse {

    private HttpStatus status;
    private final OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.status = HttpStatus.OK;
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
}
