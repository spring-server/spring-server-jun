package project.server.mvc.springframework.web.servlet;

import java.util.List;
import static java.util.List.of;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.ServletException;
import static project.server.mvc.servlet.http.ContentType.APPLICATION_JSON;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;
import project.server.mvc.springframework.web.servlet.mvc.method.RequestMappingHandlerMapping;
import project.server.mvc.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class DispatcherServlet extends FrameworkServlet {

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;
    private GlobalExceptionHandler exceptionHandler;

    public DispatcherServlet() {
        init();
    }

    @Override
    public void init() {
        initStrategies();
    }

    @Override
    protected void doService(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        doDispatch(request, response);
    }

    private void doDispatch(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        try {
            HandlerExecutionChain handler = getHandler(request);
            if (handler == null) {
                return;
            }

            // 구현 편의를 위해 반환타입을 void로 지정
            handler.applyPreHandle(request, response);

            HandlerAdapter handlerAdapter = getHandlerAdapter(handler.getHandler());
            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler.getHandler());
            processDispatchResult(request, response, modelAndView);
        } catch (Exception exception) {
            exceptionHandler.resolveException(response, exception);
            StaticView view = new StaticView();
            view.render(null, request, response);
        }
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
        if (modelAndView == null) {
            if (request.isContentType(APPLICATION_JSON)) {
                return;
            }
            throw new RuntimeException("올바르지 않은 요청입니다.");
        }
        render(modelAndView, request, response);
    }

    private void render(
        ModelAndView modelAndView,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        String viewName = modelAndView.getViewName();
        View view = resolveViewName(viewName);
        view.render(modelAndView, request, response);
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

    private void initStrategies() {
        initHandlerMappings();
        initHandlerAdapters();
        initViewResolvers();
        initExceptionHandler();
    }

    private void initHandlerMappings() {
        super.init();
        RequestMappingHandlerMapping bean = getBean(RequestMappingHandlerMapping.class);
        this.handlerMappings = of(bean);
    }

    private void initHandlerAdapters() {
        RequestMappingHandlerAdapter bean = getBean(RequestMappingHandlerAdapter.class);
        this.handlerAdapters = of(bean);
    }

    private void initViewResolvers() {
        this.viewResolvers = of(new BeanNameViewResolver());
    }

    private void initExceptionHandler() {
        this.exceptionHandler = new GlobalExceptionHandler();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
