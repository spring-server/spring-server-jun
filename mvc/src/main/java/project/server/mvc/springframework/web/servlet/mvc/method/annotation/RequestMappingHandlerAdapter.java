package project.server.mvc.springframework.web.servlet.mvc.method.annotation;

import project.server.mvc.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import project.server.mvc.springframework.web.servlet.ModelAndView;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

public class RequestMappingHandlerAdapter extends AbstractHandlerMethodAdapter {

    @Override
    public ModelAndView handle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        return super.handle(request, response, handler);
    }
}
