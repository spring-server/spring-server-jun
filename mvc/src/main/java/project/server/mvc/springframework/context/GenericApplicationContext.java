package project.server.mvc.springframework.context;

public class GenericApplicationContext extends AbstractApplicationContext {

    @Override
    public Object getBean(String name) {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }
}
