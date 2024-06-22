package project.server.mvc.springframework.web.servlet.mvc.method;

import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.springframework.web.method.HandlerMethod;
import project.server.mvc.springframework.web.servlet.HandlerExecutionChain;

public class RequestMappingHandlerMapping extends RequestMappingInfoHandlerMapping {

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        HandlerMethod handlerMethod = getHandlerInternal(request);
        return getHandlerExecutionChain(handlerMethod, request);
    }

    @Override
    public HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
        Object handler = super.getHandlerInternal(request);
        return (HandlerMethod) handler;
    }
}
