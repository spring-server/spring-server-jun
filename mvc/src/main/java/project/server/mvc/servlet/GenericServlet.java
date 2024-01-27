package project.server.mvc.servlet;

public abstract class GenericServlet implements Servlet {

    @Override
    public void init() {
    }

    @Override
    public abstract void service(ServletRequest request, ServletResponse response) throws Exception;

    @Override
    public void destroy() {
        // NOOP by default
    }
}
