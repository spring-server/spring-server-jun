package project.server.mvc.servlet;

import project.server.mvc.servlet.http.HttpMethod;

public abstract class HttpServlet extends GenericServlet {

    @Override
    public void service(
        ServletRequest request,
        ServletResponse response
    ) throws Exception {
        HttpServletRequest httpServletRequest;
        HttpServletResponse httpServletResponse;

        httpServletRequest = (HttpServletRequest) request;
        httpServletResponse = (HttpServletResponse) response;

        service(httpServletRequest, httpServletResponse);
    }

    protected void service(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        HttpMethod method = request.getMethod();
        if (method.isGet()) {
            doGet(request, response);
            return;
        }
        if (method.isPost()) {
            doPost(request, response);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }
}
