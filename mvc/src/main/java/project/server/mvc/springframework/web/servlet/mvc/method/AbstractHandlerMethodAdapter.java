package project.server.mvc.springframework.web.servlet.mvc.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import project.server.mvc.springframework.web.servlet.HandlerAdapter;
import project.server.mvc.springframework.web.method.HandlerMethod;
import project.server.mvc.springframework.web.servlet.ModelAndView;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.HttpServletRequest;

public abstract class AbstractHandlerMethodAdapter implements HandlerAdapter {

    @Override
    public final boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public ModelAndView handle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        return invokeHandlerMethod(request, response, handler);
    }

    private ModelAndView invokeHandlerMethod(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws IllegalAccessException, InvocationTargetException {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (handlerMethod.handleStaticResource()) {
            return new ModelAndView();
        }

        Object instance = handlerMethod.getHandler();
        Object[] args = new Object[]{request, response};
        Object result = method.invoke(instance, args);
        return (ModelAndView) result;
    }
}
