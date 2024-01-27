package project.server.mvc.servlet;

import java.io.IOException;
import java.io.OutputStream;

public interface ServletResponse {
    OutputStream getOutputStream() throws IOException;
}
