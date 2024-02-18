package project.server.mvc.springframework.web.servlet.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.http.HttpMethod;
import project.server.mvc.springframework.context.ApplicationContext;
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

    class MappingRegistry {

        private final Map<RequestMappingInfo, MappingRegistration> registry;

        public MappingRegistry() {
            this.registry = new HashMap<>();
            Object homeController = Optional.ofNullable(ApplicationContext.getBean("HomeController")).get();
            Object signUpController = Optional.ofNullable(ApplicationContext.getBean("SignUpController")).get();
            Object loginUpController = Optional.ofNullable(ApplicationContext.getBean("LoginController")).get();
            Object userInfoController = Optional.ofNullable(ApplicationContext.getBean("UserInfoSearchController")).get();

            registry.put(
                new RequestMappingInfo(HttpMethod.GET, "/"),
                new MappingRegistration(new HandlerMethod(homeController))
            );
            registry.put(
                new RequestMappingInfo(HttpMethod.GET, "/my-info"),
                new MappingRegistration(new HandlerMethod(userInfoController))
            );
            registry.put(
                new RequestMappingInfo(HttpMethod.POST, "/sign-up"),
                new MappingRegistration(new HandlerMethod(signUpController))
            );
            registry.put(
                new RequestMappingInfo(HttpMethod.POST, "/sign-in"),
                new MappingRegistration(new HandlerMethod(loginUpController))
            );
        }

        public MappingRegistration getMappingRegistration(RequestMappingInfo requestMappingInfo) {
            return registry.get(requestMappingInfo);
        }
    }

    public static class MappingRegistration {

        private final HandlerMethod handlerMethod;

        public MappingRegistration(HandlerMethod handlerMethod) {
            this.handlerMethod = handlerMethod;
        }

        public HandlerMethod getHandlerMethod() {
            return handlerMethod;
        }
    }
}
