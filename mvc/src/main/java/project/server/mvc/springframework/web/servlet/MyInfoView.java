package project.server.mvc.springframework.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.http.HttpStatus;
import static project.server.mvc.servlet.http.HttpStatus.UN_AUTHORIZED;
import project.server.mvc.springframework.ui.ModelMap;

public class MyInfoView implements View {

    private static final int BASE_OFFSET = 0;
    private static final int DEFAULT_BUFFER_SIZE = 1_024;
    private static final int EMPTY = -1;
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String MASKING = "*";
    private static final String STATIC_PREFIX = "static";

    @Override
    public void render(
        ModelAndView modelAndView,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        HttpStatus httpStatus = response.getStatus();
        if (UN_AUTHORIZED.equals(httpStatus)) {
            response.setStatus(UN_AUTHORIZED);
            return;
        }

        ModelMap modelMap = modelAndView.getModelMap();
        InputStream inputStream = getInputStream(STATIC_PREFIX + request.getRequestUri());
        byte[] buffer = getBuffer(inputStream, modelMap);

        setResponseHeader(request, response, buffer.length);
        setResponseBody(response, buffer);
    }

    private InputStream getInputStream(String path) {
        return getClass().getClassLoader()
            .getResourceAsStream(path);
    }

    private void setResponseHeader(
        HttpServletRequest request,
        HttpServletResponse response,
        int lengthOfBodyContent
    ) {
        response.setHeader(CONTENT_TYPE, request.getContentType());
        response.setHeader(CONTENT_LENGTH, valueOf(lengthOfBodyContent));
    }

    private void setResponseBody(
        HttpServletResponse response,
        byte[] buffer
    ) {
        response.setBody(new String(buffer));
    }

    private byte[] getBuffer(
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
