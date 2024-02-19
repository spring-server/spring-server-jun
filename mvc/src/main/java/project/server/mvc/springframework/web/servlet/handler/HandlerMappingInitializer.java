package project.server.mvc.springframework.web.servlet.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import project.server.mvc.servlet.http.HttpMethod;
import project.server.mvc.springframework.annotation.Controller;
import project.server.mvc.springframework.annotation.DeleteMapping;
import project.server.mvc.springframework.annotation.GetMapping;
import project.server.mvc.springframework.annotation.PostMapping;
import project.server.mvc.springframework.annotation.PutMapping;
import static project.server.mvc.springframework.context.ApplicationContext.findByAnnotation;
import project.server.mvc.springframework.handler.RequestMappingInfo;
import project.server.mvc.springframework.web.method.HandlerMethod;
import project.server.mvc.springframework.web.servlet.Handler;

public class HandlerMappingInitializer {

    private Map<RequestMappingInfo, AbstractHandlerMethodMapping.MappingRegistration> registry = new HashMap<>();

    public HandlerMappingInitializer() {
        List<Handler> handlers = findByAnnotation(Controller.class).stream()
            .map(bean -> (Handler) bean)
            .toList();

        for (Handler handler : handlers) {
            registerHandlerMethod(handler);
        }
    }

    private void registerHandlerMethod(Handler handler) {
        Method[] methods = handler.getClass().getDeclaredMethods();
        for (Method method : methods) {
            registerGetMapping(handler, method);
            registerPostMapping(handler, method);
            registerPutMapping(handler, method);
            registerDeleteMapping(handler, method);
        }
    }

    private void registerGetMapping(
        Handler handler,
        Method method
    ) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            String url = getMapping.path();
            RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.GET, url);
            HandlerMethod handlerMethod = new HandlerMethod(handler);
            registerMapping(requestMappingInfo, handlerMethod);
        }
    }

    private void registerPostMapping(
        Handler handler,
        Method method
    ) {
        if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping getMapping = method.getAnnotation(PostMapping.class);
            String url = getMapping.path();
            RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.POST, url);
            HandlerMethod handlerMethod = new HandlerMethod(handler);
            registerMapping(requestMappingInfo, handlerMethod);
        }
    }

    private void registerPutMapping(
        Handler handler,
        Method method
    ) {
        if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping getMapping = method.getAnnotation(PutMapping.class);
            String url = getMapping.path();
            RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.PUT, url);
            HandlerMethod handlerMethod = new HandlerMethod(handler);
            registerMapping(requestMappingInfo, handlerMethod);
        }
    }

    private void registerDeleteMapping(
        Handler handler,
        Method method
    ) {
        if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping getMapping = method.getAnnotation(DeleteMapping.class);
            String url = getMapping.path();
            RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.DELETE, url);
            HandlerMethod handlerMethod = new HandlerMethod(handler);
            registerMapping(requestMappingInfo, handlerMethod);
        }
    }

    private void registerMapping(
        RequestMappingInfo requestMappingInfo,
        HandlerMethod handlerMethod
    ) {
        registry.put(requestMappingInfo, new AbstractHandlerMethodMapping.MappingRegistration(handlerMethod));
    }

    public Map<RequestMappingInfo, AbstractHandlerMethodMapping.MappingRegistration> getRegistry() {
        return registry;
    }
}
