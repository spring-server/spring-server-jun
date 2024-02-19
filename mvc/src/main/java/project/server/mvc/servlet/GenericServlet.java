package project.server.mvc.servlet;

import static project.server.mvc.springframework.context.ApplicationContext.register;
import project.server.mvc.springframework.web.method.HandlerMethod;
import project.server.mvc.springframework.web.servlet.handler.HandlerMappingInitializer;
import project.server.mvc.springframework.web.servlet.mvc.method.RequestMappingHandlerMapping;
import project.server.mvc.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import project.server.mvc.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public abstract class GenericServlet implements Servlet {

    @Override
    public void init() {
        register(new HandlerMethod(new ResourceHttpRequestHandler()));
        register(new HandlerMappingInitializer());
        register(new RequestMappingHandlerMapping());
        register(new RequestMappingHandlerAdapter());
    }

    @Override
    public abstract void service(ServletRequest request, ServletResponse response) throws Exception;

    @Override
    public void destroy() {
        // NOOP by default
    }
}
