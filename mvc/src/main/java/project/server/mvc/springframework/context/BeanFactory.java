package project.server.mvc.springframework.context;

public interface BeanFactory {
    Object getBean(String name);

    boolean containsBean(String name);
}
