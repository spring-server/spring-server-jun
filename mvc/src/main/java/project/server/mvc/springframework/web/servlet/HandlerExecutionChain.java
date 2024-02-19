package project.server.mvc.springframework.web.servlet;

import java.util.List;
import java.util.Objects;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.springframework.handler.HandlerInterceptor;

public class HandlerExecutionChain {

    private List<HandlerInterceptor> interceptors;
    private final Object handler;

    public HandlerExecutionChain(
        List<HandlerInterceptor> interceptors,
        Object handler
    ) {
        this.interceptors = interceptors;
        this.handler = handler;
    }

    public HandlerExecutionChain(Object handler) {
        this.handler = handler;
    }

    public Object getHandler() {
        return handler;
    }

    void applyPreHandle(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        if (interceptors == null || interceptors.isEmpty()) {
            return;
        }
        for (HandlerInterceptor interceptor : interceptors) {
            interceptor.preHandle(request, response, handler);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        HandlerExecutionChain that = (HandlerExecutionChain) object;
        return handler.equals(that.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handler);
    }

    @Override
    public String toString() {
        return String.format("handler:%s, interceptors:%s", handler, interceptors);
    }
}
