package project.server.mvc.springframework.web.servlet;

import java.io.IOException;
import java.util.List;
import project.server.mvc.servlet.HttpServletBean;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.springframework.web.method.HandlerMethod;
import project.server.mvc.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public abstract class FrameworkServlet extends HttpServletBean {

    private static final HandlerMethod staticResourceHandlerMethod = new HandlerMethod(new ResourceHttpRequestHandler());
    private static final String EMPTY_STRING = "";
    private static final List<String> staticResources = List.of(".js", ".css", ".favicon", ".jpg", ".jpeg", ".png");
    private static final List<String> excludeStaticResources = List.of("my-info.html");

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        if (isStaticResource(request)) {
            processStaticRequest(request, response);
            return;
        }
        processRequest(request, response);
    }

    private boolean isStaticResource(HttpServletRequest request) {
        String uri = request.getRequestUri();
        String[] parsedUri = uri.split("/");
        for (String eachUri : parsedUri) {
            if (EMPTY_STRING.equals(eachUri)) {
                continue;
            }
            if (staticResources.contains(eachUri)) {
                return true;
            }
        }
        for (String eachUri : parsedUri) {
            if (excludeStaticResources.contains(eachUri)) {
                return false;
            }
        }
        return true;
    }

    private void processStaticRequest(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        ResourceHttpRequestHandler handler = (ResourceHttpRequestHandler) staticResourceHandlerMethod.getHandler();
        handler.handleRequest(request, response);
    }

    protected final void processRequest(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        doService(request, response);
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        processRequest(request, response);
    }

    protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
