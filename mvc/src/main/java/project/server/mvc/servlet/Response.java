package project.server.mvc.servlet;

import java.io.OutputStream;
import project.server.mvc.servlet.HttpServletResponse;

public class Response implements HttpServletResponse {

    private final OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
