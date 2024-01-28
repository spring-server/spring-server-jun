package project.server.mvc.springframework.web.servlet.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import static project.server.mvc.servlet.http.HttpStatus.NOT_FOUND;
import project.server.mvc.springframework.web.HttpRequestHandler;

public class ResourceHttpRequestHandler implements HttpRequestHandler {

    private static final String CARRIAGE_RETURN = "\r\n";
    private static final int BASE_OFFSET = 0;
    private static final int START_INDEX = 1;
    private static final int EMPTY = -1;
    private static final String CONTENT_LENGTH = "Content-Length: ";
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String DELIMITER = " ";
    private static final String HTML = ".html";
    private static final String STATIC_PREFIX = "static";

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestUri();
        if (isStaticPage(request)) {
            InputStream inputStream = getInputStream(STATIC_PREFIX + uri);
            if (inputStream != null) {
                response(request, response, inputStream);
                return;
            }
        }
        InputStream inputStream = getInputStream(getFile(uri));
        if (inputStream != null) {
            response(request, response, inputStream);
            return;
        }
        responsePageNotFound(response);
    }

    private boolean isStaticPage(HttpServletRequest request) {
        String url = request.getRequestUri();
        return url.contains(HTML);
    }

    private String getFile(String uri) {
        try {
            return uri.substring(START_INDEX);
        } catch (IndexOutOfBoundsException exception) {
            throw new RuntimeException("올바른 경로를 입력해주세요.");
        } catch (NullPointerException exception) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }
    }

    private InputStream getInputStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    private void response(
        HttpServletRequest request,
        HttpServletResponse response,
        InputStream inputStream
    ) throws IOException {
        byte[] buffer = readStream(inputStream);
        setResponseHeader(request, response, buffer.length);
        responseBody(response, buffer);
    }

    private void responsePageNotFound(HttpServletResponse response) {
        response.setStatus(NOT_FOUND);
    }

    private void responseBody(HttpServletResponse response, byte[] body) throws IOException {
        SocketChannel channel = response.getSocketChannel(); // 가정: HttpServletResponse에 getSocketChannel() 메서드가 있다.
        ByteBuffer buffer = ByteBuffer.wrap(body);
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    private void setResponseHeader(
        HttpServletRequest request,
        HttpServletResponse response,
        int lengthOfBodyContent
    ) throws IOException {
        SocketChannel channel = response.getSocketChannel();
        String header = request.getHttpVersion() + DELIMITER + getStatus(response) + CARRIAGE_RETURN +
            CONTENT_TYPE + request.getContentType() + CARRIAGE_RETURN +
            CONTENT_LENGTH + lengthOfBodyContent + CARRIAGE_RETURN +
            CARRIAGE_RETURN;
        ByteBuffer headerBuffer = ByteBuffer.wrap(header.getBytes(StandardCharsets.UTF_8));
        while (headerBuffer.hasRemaining()) {
            channel.write(headerBuffer);
        }
    }

    private static String getStatus(HttpServletResponse response) {
        return response.getStatusAsString() + DELIMITER;
    }

    private byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != EMPTY) {
            byteArrayOutputStream.write(buffer, BASE_OFFSET, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
