package project.server.mvc.springframework.web;

import java.util.ArrayList;
import java.util.List;
import project.server.mvc.springframework.handler.HandlerInterceptor;

public class InterceptorRegistry {

    private final List<InterceptorRegistration> registrations = new ArrayList<>();

    public List<InterceptorRegistration> getInterceptors() {
        return registrations;
    }

    public InterceptorRegistration addInterceptor(HandlerInterceptor interceptor) {
        InterceptorRegistration registration = new InterceptorRegistration(interceptor);
        this.registrations.add(registration);
        return registration;
    }
}
