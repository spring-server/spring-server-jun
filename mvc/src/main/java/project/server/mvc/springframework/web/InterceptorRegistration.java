package project.server.mvc.springframework.web;

import java.util.ArrayList;
import java.util.List;
import project.server.mvc.springframework.handler.HandlerInterceptor;

public class InterceptorRegistration {

    private final List<String> urls = new ArrayList<>();
    private final HandlerInterceptor handlerInterceptor;

    public InterceptorRegistration(HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
    }

    public List<String> getUrls() {
        return urls;
    }

    public boolean contains(String url) {
        return urls.contains(url);
    }

    public HandlerInterceptor getHandlerInterceptor() {
        return handlerInterceptor;
    }

    public void addPathPatterns(String... url) {
        this.urls.addAll(List.of(url));
    }

    @Override
    public String toString() {
        return String.format("urls:%s, interceptor:%s", urls, handlerInterceptor);
    }
}
