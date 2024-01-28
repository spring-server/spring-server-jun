package project.server.mvc.springframework.web.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

public class RedirectView implements View {

    private static final String CARRIAGE_RETURN = "\r\n";
    private static final String PROTOCOL = "http://";
    private static final String DELIMITER = " ";
    private static final String LOCATION_DELIMITER = "Location: ";
    private static final String REDIRECT_LOCATION = "redirect:/index.html";
    private static final String HOME = "/index.html";

    private final Map<String, View> views = new HashMap<>();

    public RedirectView() {
        views.put(REDIRECT_LOCATION, new StaticView());
    }

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
        DataOutputStream dos = new DataOutputStream(response.getOutputStream());
        dos.writeBytes(getStartLine(request, response));
        dos.writeBytes(LOCATION_DELIMITER + getRedirectLocation(request) + CARRIAGE_RETURN);
        dos.writeBytes("Cookie: " + response.getCookiesAsString() + CARRIAGE_RETURN);
        dos.writeBytes(CARRIAGE_RETURN);
        dos.flush();
        dos.close();
    }

    private String getStartLine(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        return String.format("%s%s%s%s", request.getHttpVersion(), DELIMITER, getStatus(response), CARRIAGE_RETURN);
    }

    private static String getStatus(HttpServletResponse response) {
        return String.format("%s%s", response.getStatusAsString(), DELIMITER);
    }

    private String getRedirectLocation(HttpServletRequest request) {
        return String.format("%s%s%s", PROTOCOL, request.getHost(), HOME);
    }
}
