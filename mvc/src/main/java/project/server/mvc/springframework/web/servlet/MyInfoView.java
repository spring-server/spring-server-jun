package project.server.mvc.springframework.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import static java.nio.charset.StandardCharsets.UTF_8;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.springframework.ui.ModelMap;

public class MyInfoView implements View {

    private static final String CARRIAGE_RETURN = "\r\n";
    private static final int BASE_OFFSET = 0;
    private static final int DEFAULT_BUFFER_SIZE = 1_024;
    private static final int EMPTY = -1;
    private static final String CONTENT_LENGTH = "Content-Length: ";
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String DELIMITER = " ";
    private static final String MASKING = "*";
    private static final String STATIC_PREFIX = "static";

    @Override
    public void render(
        ModelAndView modelAndView,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        ModelMap modelMap = modelAndView.getModelMap();
        InputStream inputStream = getInputStream(STATIC_PREFIX + request.getRequestUri());
        byte[] buffer = readStream(inputStream, modelMap);
        setResponseHeader(request, response, buffer.length);
        responseBody(response, buffer);
    }

    private InputStream getInputStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    private void responseBody(
        HttpServletResponse response,
        byte[] body
    ) throws IOException {
        SocketChannel channel = response.getSocketChannel();
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
        String header = request.getHttpVersion()
            + DELIMITER
            + getStatus(response)
            + CARRIAGE_RETURN
            + CONTENT_TYPE
            + request.getContentType()
            + CARRIAGE_RETURN
            + CONTENT_LENGTH
            + lengthOfBodyContent
            + CARRIAGE_RETURN
            + CARRIAGE_RETURN;
        ByteBuffer headerBuffer = ByteBuffer.wrap(header.getBytes(UTF_8));
        while (headerBuffer.hasRemaining()) {
            channel.write(headerBuffer);
        }
    }

    private static String getStatus(HttpServletResponse response) {
        return response.getStatusAsString() + DELIMITER;
    }

    private byte[] readStream(
        InputStream inputStream,
        ModelMap modelMap
    ) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != EMPTY) {
            byteArrayOutputStream.write(buffer, BASE_OFFSET, bytesRead);
        }
        String convertedHtml = getConvertedHtml(modelMap, byteArrayOutputStream);
        return convertedHtml.getBytes(UTF_8);
    }

    private static String getConvertedHtml(
        ModelMap modelMap,
        ByteArrayOutputStream byteArrayOutputStream
    ) {
        Object username = modelMap.getAttribute("username");
        Object password = modelMap.getAttribute("password");

        String htmlPage = byteArrayOutputStream.toString(UTF_8);
        String replacedUsernameHtml = htmlPage.replace(MASKING, username.toString());
        return replacedUsernameHtml.replace("\\", MASKING.repeat(password.toString().length()));
    }
}
