package project.server.mvc.springframework.web.servlet;

public interface ViewResolver {
    View resolveViewName(String viewName);
}
