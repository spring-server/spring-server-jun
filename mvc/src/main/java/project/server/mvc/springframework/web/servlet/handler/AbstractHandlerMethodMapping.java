package project.server.mvc.springframework.web.servlet.handler;

import java.util.Map;
import project.server.mvc.servlet.HttpServletRequest;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;
import project.server.mvc.springframework.handler.RequestMappingInfo;
import project.server.mvc.springframework.web.method.HandlerMethod;

public abstract class AbstractHandlerMethodMapping extends AbstractHandlerMapping {

    private final MappingRegistry mappingRegistry = new MappingRegistry();

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        String lookupPath = initLookupPath(request);
        return lookupHandlerMethod(lookupPath, request);
    }

    private String initLookupPath(HttpServletRequest request) {
        return getRequestPath(request);
    }

    private String getRequestPath(HttpServletRequest request) {
        return request.getRequestUri()
            .replace(".html", "");
    }

    private HandlerMethod lookupHandlerMethod(
        String lookupPath,
        HttpServletRequest request
    ) {
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo(request.getMethod(), lookupPath);
        MappingRegistration registration = mappingRegistry.getMappingRegistration(requestMappingInfo);
        return registration != null ? registration.handlerMethod : null;
    }

    static class MappingRegistry {

        private final Map<RequestMappingInfo, MappingRegistration> registry;

        public MappingRegistry() {
            Object bean = getBean(HandlerMappingInitializer.class);
            HandlerMappingInitializer handlerMappingInitializer = (HandlerMappingInitializer) bean;
            this.registry = handlerMappingInitializer.getRegistry();
        }

        public MappingRegistration getMappingRegistration(RequestMappingInfo requestMappingInfo) {
            return registry.get(requestMappingInfo);
        }
    }

    static class MappingRegistration {

        private final HandlerMethod handlerMethod;

        public MappingRegistration(HandlerMethod handlerMethod) {
            this.handlerMethod = handlerMethod;
        }

        public HandlerMethod getHandlerMethod() {
            return handlerMethod;
        }
    }
}
