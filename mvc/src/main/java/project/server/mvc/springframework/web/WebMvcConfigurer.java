package project.server.mvc.springframework.web;

public interface WebMvcConfigurer {
    void addInterceptors(InterceptorRegistry registry);
}
