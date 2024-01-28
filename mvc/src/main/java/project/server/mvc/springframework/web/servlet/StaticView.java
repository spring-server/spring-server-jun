package project.server.mvc.springframework.web.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import static java.nio.charset.StandardCharsets.UTF_8;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

public class StaticView implements View {

    private static final String CARRIAGE_RETURN = "\r\n";
    private static final String DELIMITER = " ";
    private static final String LOCATION_DELIMITER = "Location: ";
    private static final String REDIRECT_LOCATION = "/index.html";

    @Override
    public void render(
        ModelAndView modelAndView,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        response(request, response);
    }

    private void response(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        setResponseHeader(request, response);
    }

    private void setResponseHeader(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        SocketChannel channel = response.getSocketChannel();
        StringBuilder headerBuilder = getStringBuilder(response);

        String header = headerBuilder.toString();
        ByteBuffer headerBuffer = ByteBuffer.wrap(header.getBytes(UTF_8));
        while (headerBuffer.hasRemaining()) {
            channel.write(headerBuffer);
        }
    }

    private StringBuilder getStringBuilder(HttpServletResponse response) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(getStartLine(response));
        headerBuilder.append(CARRIAGE_RETURN);
        return headerBuilder;
    }

    private String getStartLine(HttpServletResponse response) {
        return String.format("HTTP/1.1 %s%s", response.getStatusAsString(), CARRIAGE_RETURN);
    }
}
