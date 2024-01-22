package project.server.mvc.springframework.web.servlet;

import java.util.List;
import project.server.mvc.springframework.web.servlet.mvc.method.RequestMappingHandlerMapping;
import project.server.mvc.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import project.server.mvc.servlet.ServletException;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;

public class DispatcherServlet extends FrameworkServlet {

    private final List<HandlerMapping> handlerMappings;
    private final List<HandlerAdapter> handlerAdapters;
    private final List<ViewResolver> viewResolvers;

    public DispatcherServlet() {
        this.handlerMappings = List.of(new RequestMappingHandlerMapping());
        this.handlerAdapters = List.of(new RequestMappingHandlerAdapter());
        this.viewResolvers = List.of();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void doService(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        HandlerExecutionChain handler = getHandler(request);
        if (handler == null) {
            return;
        }

        HandlerAdapter handlerAdapter = getHandlerAdapter(handler.getHandler());
        ModelAndView modelAndView = handlerAdapter.handle(request, response, handler.getHandler());
        applyDefaultViewName(request, response, modelAndView);
        processDispatchResult(request, response, modelAndView);
    }

    private void applyDefaultViewName(
        HttpServletRequest request,
        HttpServletResponse response,
        ModelAndView modelAndView
    ) {
    }

    private HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (this.handlerMappings != null) {
            for (HandlerMapping mapping : this.handlerMappings) {
                HandlerExecutionChain handler = mapping.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }

    protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
        if (this.handlerAdapters != null) {
            for (HandlerAdapter adapter : this.handlerAdapters) {
                if (adapter.supports(handler)) {
                    return adapter;
                }
            }
        }
        throw new ServletException();
    }

    private void processDispatchResult(
        HttpServletRequest request,
        HttpServletResponse response,
        ModelAndView modelAndView
    ) throws Exception {
        render(modelAndView, request, response);
    }

    private void render(
        ModelAndView modelAndView,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        String viewName = modelAndView.getViewName();
        View view = resolveViewName(viewName);
        view.render(modelAndView.getModelMap(), request, response);
    }

    protected View resolveViewName(String viewName) {
        if (this.viewResolvers != null) {
            for (ViewResolver viewResolver : this.viewResolvers) {
                View view = viewResolver.resolveViewName(viewName);
                if (view != null) {
                    return view;
                }
            }
        }
        return null;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
