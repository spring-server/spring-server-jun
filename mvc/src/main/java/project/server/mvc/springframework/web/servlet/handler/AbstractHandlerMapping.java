package project.server.mvc.springframework.web.servlet.handler;

import project.server.mvc.springframework.web.servlet.HandlerExecutionChain;
import project.server.mvc.springframework.web.servlet.HandlerMapping;
import project.server.mvc.servlet.HttpServletRequest;

public abstract class AbstractHandlerMapping implements HandlerMapping {

    private Object defaultHandler;

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        Object handler = getHandlerInternal(request);
        return new HandlerExecutionChain(handler);
    }

    public Object getDefaultHandler() {
        return this.defaultHandler;
    }

    protected abstract Object getHandlerInternal(HttpServletRequest request) throws Exception;
}
