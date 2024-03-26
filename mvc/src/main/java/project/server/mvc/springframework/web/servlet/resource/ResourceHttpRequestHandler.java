package project.server.mvc.springframework.web.servlet.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import static project.server.mvc.servlet.http.HttpStatus.NOT_FOUND;
import project.server.mvc.springframework.web.HttpRequestHandler;

public class ResourceHttpRequestHandler implements HttpRequestHandler {

    private static final int BASE_OFFSET = 0;
    private static final int START_INDEX = 1;
    private static final int EMPTY = -1;
    private static final int BUFFER_CAPACITY = 1_024;
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String HTML = ".html";
    private static final String STATIC_PREFIX = "static";


    @Override
    public void handleRequest(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
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
        return getClass().getClassLoader()
            .getResourceAsStream(path);
    }

    private void response(
        HttpServletRequest request,
        HttpServletResponse response,
        InputStream inputStream
    ) throws IOException {
        byte[] buffer = readInputStream(inputStream);
        setResponseHeader(request, response, buffer.length);
        setResponseBody(response, buffer);
    }

    private void responsePageNotFound(HttpServletResponse response) {
        response.setStatus(NOT_FOUND);
    }

    private void setResponseBody(
        HttpServletResponse response,
        byte[] body
    ) throws IOException {
        response.write(body);
    }

    private void setResponseHeader(
        HttpServletRequest request,
        HttpServletResponse response,
        int lengthOfBodyContent
    ) throws IOException {
        response.getHttpHeaderLine();
        response.setHeader(CONTENT_TYPE, request.getContentTypeAsString());
        response.setHeader(CONTENT_LENGTH, String.valueOf(lengthOfBodyContent));

        String header = response.getHttpHeaderLine();
        response.write(header);
    }

    private byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_CAPACITY];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != EMPTY) {
            byteArrayOutputStream.write(buffer, BASE_OFFSET, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
