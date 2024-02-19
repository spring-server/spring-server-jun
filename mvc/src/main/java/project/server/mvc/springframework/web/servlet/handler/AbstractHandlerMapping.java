package project.server.mvc.springframework.web.servlet.handler;

import java.util.ArrayList;
import java.util.List;
import project.server.mvc.servlet.HttpServletRequest;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;
import project.server.mvc.springframework.handler.HandlerInterceptor;
import project.server.mvc.springframework.web.InterceptorRegistration;
import project.server.mvc.springframework.web.InterceptorRegistry;
import project.server.mvc.springframework.web.servlet.HandlerExecutionChain;
import project.server.mvc.springframework.web.servlet.HandlerMapping;

public abstract class AbstractHandlerMapping implements HandlerMapping {

    private final List<InterceptorRegistration> interceptors;

    public AbstractHandlerMapping() {
        InterceptorRegistry registration = getBean(InterceptorRegistry.class);
        this.interceptors = registration.getInterceptors();
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        Object handler = getHandlerInternal(request);
        return getHandlerExecutionChain(handler, request);
    }

    protected abstract Object getHandlerInternal(HttpServletRequest request) throws Exception;

    protected HandlerExecutionChain getHandlerExecutionChain(
        Object handler,
        HttpServletRequest request
    ) {
        List<HandlerInterceptor> interceptors = new ArrayList<>();
        for (InterceptorRegistration registration : this.interceptors) {
            if (registration.contains(request.getRequestUri())) {
                HandlerInterceptor interceptor = registration.getHandlerInterceptor();
                interceptors.add(interceptor);
                return new HandlerExecutionChain(interceptors, handler);
            }
        }
        return new HandlerExecutionChain(handler);
    }
}
