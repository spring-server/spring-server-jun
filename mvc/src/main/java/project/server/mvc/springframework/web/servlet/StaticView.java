package project.server.mvc.springframework.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.nio.charset.StandardCharsets.UTF_8;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.http.HttpStatus;
import static project.server.mvc.servlet.http.HttpStatus.MOVE_PERMANENTLY;
import static project.server.mvc.servlet.http.HttpStatus.UN_AUTHORIZED;

public class StaticView implements View {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String TEXT_HTML = "text/html";
    private static final String LOCATION = "Location";
    private static final String ROOT = "/";
    private static final String NEXT_LINE = "\n";

    @Override
    public void render(
        ModelAndView modelAndView,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        HttpStatus httpStatus = response.getStatus();
        if (UN_AUTHORIZED.equals(httpStatus)) {
            response.setStatus(MOVE_PERMANENTLY);
            response.setHeader(LOCATION, ROOT);
            return;
        }

        InputStream inputStream = getInputStream(request.getRequestUri());
        String html = readInputStream(inputStream);

        setResponseHeader(response);
        setResponseBody(response, html);
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

    private void setResponseHeader(HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, TEXT_HTML);
    }

    private void setResponseBody(
        HttpServletResponse response,
        String html
    ) {
        response.setBody(html);
    }
}
