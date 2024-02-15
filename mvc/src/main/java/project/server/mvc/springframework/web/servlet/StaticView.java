package project.server.mvc.springframework.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.nio.charset.StandardCharsets.UTF_8;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.http.HttpStatus;
import static project.server.mvc.servlet.http.HttpStatus.UN_AUTHORIZED;

public class StaticView implements View {

    private static final String COOKIE = "Set-Cookie";
    private static final String INVALID_COOKIE = "Max-Age=0; Path=/";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String TEXT_HTML = "text/html";
    private static final String INDEX_HTML = "static/index.html";
    private static final String SIGN_IN_HTML = "static/sign-in.html";
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
        HttpStatus httpStatus = response.getStatus();
        if (UN_AUTHORIZED.equals(httpStatus)) {
            InputStream inputStream = getInputStream();
            String html = readInputStream(inputStream);
            response.setHeader(CONTENT_TYPE, request.getContentType());
            response.setHeader(COOKIE, INVALID_COOKIE);
            response.setBody(html);
            return;
        }
        InputStream inputStream = getInputStream(SIGN_IN_HTML);
        String html = readInputStream(inputStream);
        response.setHeader(CONTENT_TYPE, TEXT_HTML);
        response.setBody(html);
    }

    private InputStream getInputStream() {
        return getClass().getClassLoader()
            .getResourceAsStream(INDEX_HTML);
    }

    private InputStream getInputStream(String path) {
        return getClass().getClassLoader()
            .getResourceAsStream(path);
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
