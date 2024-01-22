package project.server.mvc.springframework.web.servlet;

import java.util.Objects;

public class HandlerExecutionChain {

    private final Object handler;

    public HandlerExecutionChain(Object handler) {
        this.handler = handler;
    }

    public Object getHandler() {
        return handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerExecutionChain that = (HandlerExecutionChain) o;
        return handler.equals(that.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handler);
    }

    @Override
    public String toString() {
        return String.format("%s", handler);
    }
}
