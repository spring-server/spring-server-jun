package project.server.mvc.springframework.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import static java.nio.charset.StandardCharsets.UTF_8;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.http.HttpStatus;
import static project.server.mvc.servlet.http.HttpStatus.UN_AUTHORIZED;

public class StaticView implements View {

    private static final String CARRIAGE_RETURN = "\r\n";
    private static final String INVALID_COOKIE = "Set-Cookie=; Max-Age=0; Path=/";
    private static final String DELIMITER = " ";
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String TEXT_HTML = "text/html";
    private static final String HTML_REQUEST_LINE = CONTENT_TYPE + TEXT_HTML + CARRIAGE_RETURN + CARRIAGE_RETURN;
    private static final String INDEX_HTML = "static/index.html";
    private static final String NEXT_LINE = "\n";

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
        HttpStatus httpStatus = response.getStatus();
        String header = getHeader(request, response);

        ByteBuffer headerBuffer = ByteBuffer.wrap(header.getBytes(UTF_8));
        channel.write(headerBuffer);

        if (UN_AUTHORIZED.equals(httpStatus)) {
            InputStream inputStream = getInputStream();
            String htmlContent = readInputStream(inputStream);
            ByteBuffer contentTypeBuffer = ByteBuffer.wrap(HTML_REQUEST_LINE.getBytes(UTF_8));
            channel.write(contentTypeBuffer);

            ByteBuffer htmlBuffer = ByteBuffer.wrap(htmlContent.getBytes(UTF_8));
            channel.write(htmlBuffer);
        }
    }

    private InputStream getInputStream() {
        return getClass().getClassLoader()
            .getResourceAsStream(INDEX_HTML);
    }

    private String getHeader(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        HttpStatus httpStatus = response.getStatus();

        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(request.getHttpVersion())
            .append(DELIMITER)
            .append(httpStatus.getStatusCode())
            .append(DELIMITER)
            .append(httpStatus.getStatus())
            .append(CARRIAGE_RETURN);

        if (UN_AUTHORIZED.equals(httpStatus)) {
            headerBuilder.append(INVALID_COOKIE)
                .append(CARRIAGE_RETURN);
        }
        return headerBuilder.toString();
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(NEXT_LINE);
            }
        }
        return stringBuilder.toString();
    }
}
