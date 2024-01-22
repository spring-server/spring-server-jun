package project.server.mvc.springframework.web.method;

import java.lang.reflect.Method;
import java.util.Objects;
import project.server.mvc.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class HandlerMethod {

    private static final String PROCESS = "process";
    private static final String HANDLE_REQUEST = "handleRequest";

    private final Object handler;
    private final Class<?> bean;
    private final Method method;

    public HandlerMethod(Object handler) {
        this.handler = handler;
        this.bean = handler.getClass();
        this.method = getMethod(handler);
    }

    private Method getMethod(Object handler) {
        Method[] methods = handler.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (isProcess(method)) {
                return method;
            }
            if (isHandlerRequest(method)) {
                return method;
            }
        }
        throw new RuntimeException();
    }

    private boolean isProcess(Method method) {
        return method.getName().equals(PROCESS);
    }

    private boolean isHandlerRequest(Method method) {
        return method.getName().equals(HANDLE_REQUEST);
    }

    public Object getHandler() {
        return handler;
    }

    public Class<?> getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }

    public boolean handleStaticResource() {
        return handler instanceof ResourceHttpRequestHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerMethod that = (HandlerMethod) o;
        return handler.equals(that.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handler);
    }

    @Override
    public String toString() {
        return handler.toString();
    }
}
