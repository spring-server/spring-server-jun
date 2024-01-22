package project.server.mvc.springframework.web.servlet;

import java.io.IOException;
import project.server.mvc.springframework.web.method.HandlerMethod;
import project.server.mvc.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import project.server.mvc.servlet.HttpServletBean;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

public abstract class FrameworkServlet extends HttpServletBean {

    private static final HandlerMethod staticResourceHandlerMethod = new HandlerMethod(new ResourceHttpRequestHandler());
    private static final String STATIC_RESOURCE = ".";

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
        String url = request.getRequestURI();
        return url.contains(STATIC_RESOURCE);
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
