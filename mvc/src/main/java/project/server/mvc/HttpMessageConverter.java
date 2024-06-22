package project.server.mvc;

import java.io.IOException;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.http.ContentType;

public interface HttpMessageConverter {
    boolean canWrite(ContentType contentType);

    void write(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
