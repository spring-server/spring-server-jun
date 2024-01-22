package project.server.mvc.springframework.web.servlet.resource;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import project.server.mvc.springframework.web.HttpRequestHandler;
import project.server.mvc.springframework.web.servlet.StaticView;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

public class ResourceHttpRequestHandler implements HttpRequestHandler {

    private static final String CARRIAGE_RETURN = "\r\n";
    private static final String STATIC_PREFIX = "static/";

    @Override
    public void handleRequest(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        DataOutputStream out = new DataOutputStream(response.getOutputStream());
        if (isStaticPage(request)) {
            StaticView staticView = new StaticView();
            InputStream inputStream = getInputStream(STATIC_PREFIX + request.getRequestURI());
            if (inputStream != null) {
                response(request, out, inputStream);
                return;
            }
            responsePageNotFound();
            return;
        }

        InputStream inputStream = getInputStream(request.getRequestURI());
        if (inputStream != null) {
            response(request, out, inputStream);
            return;
        }
        responsePageNotFound();
    }

    private InputStream getInputStream(String path) {
        return getClass().getClassLoader()
            .getResourceAsStream(path);
    }

    private void response(
        HttpServletRequest request,
        DataOutputStream dataOutputStream,
        InputStream inputStream
    ) throws IOException {
        String contentType = request.getContentType();
        byte[] buffer = readStream(inputStream);
        responseHeader(dataOutputStream, buffer.length, contentType);
        responseBody(dataOutputStream, buffer);
    }

    private void responsePageNotFound() {

    }

    private boolean isStaticPage(HttpServletRequest request) {
        String url = request.getRequestURI();
        return url.contains("html");
    }

    private void responseBody(
        OutputStream outputStream,
        byte[] body
    ) throws IOException {
        outputStream.write(body);
        outputStream.flush();
    }

    private void responseHeader(
        OutputStream outputStream,
        int lengthOfBodyContent,
        String contentType
    ) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        dos.writeBytes("HTTP/1.1 200 OK " + CARRIAGE_RETURN);
        dos.writeBytes("Content-Type: " + contentType + CARRIAGE_RETURN);
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + CARRIAGE_RETURN);
        dos.writeBytes(CARRIAGE_RETURN);
    }

    private byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
