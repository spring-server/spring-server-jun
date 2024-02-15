package project.server.mvc.springframework.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.HashMap;
import java.util.Map;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import static project.server.mvc.servlet.http.HttpStatus.MOVE_PERMANENTLY;

public class RedirectView implements View {

    private static final String PROTOCOL = "http://";
    private static final String LOCATION_DELIMITER = "Location";
    private static final String REDIRECT_LOCATION = "redirect:/index.html";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String INDEX_HTML = "/index.html";
    private static final String SIGN_IN_HTML = "static/sign-in.html";
    private static final String TEXT_HTML = "text/html";

    public RedirectView() {
        Map<String, View> views = new HashMap<>();
        views.put(REDIRECT_LOCATION, new StaticView());
    }

    @Override
    public void render(
        ModelAndView modelAndView,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (MOVE_PERMANENTLY.equals(response.getStatus())) {
            response.setHeader(LOCATION_DELIMITER, getRedirectLocation(request));
            return;
        }
        InputStream inputStream = getInputStream();
        byte[] buffer = getBuffer(inputStream);
        setResponseHeader(response, buffer.length);
        response.setBody(new String(buffer));
    }

    private String getRedirectLocation(HttpServletRequest request) {
        return String.format("%s%s%s", PROTOCOL, request.getHost(), INDEX_HTML);
    }

    private InputStream getInputStream() {
        return getClass().getClassLoader()
            .getResourceAsStream(SIGN_IN_HTML);
    }

    private byte[] getBuffer(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }
        return stringBuilder.toString()
            .getBytes();
    }

    private void setResponseHeader(
        HttpServletResponse response,
        long lengthOfBodyContent
    ) {
        response.setHeader(CONTENT_TYPE, TEXT_HTML);
        response.setHeader(CONTENT_LENGTH, valueOf(lengthOfBodyContent));
    }
}
