package project.server.app.common.configuration.interceptor;

import lombok.extern.slf4j.Slf4j;
import project.server.app.common.login.LoginUser;
import project.server.app.common.login.Session;
import static project.server.app.common.utils.HeaderUtils.getSessionId;
import project.server.app.core.web.user.application.UserLoginUseCase;
import project.server.app.core.web.user.presentation.validator.UserValidator;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.springframework.handler.HandlerInterceptor;
import project.server.mvc.springframework.web.servlet.ModelAndView;

@Slf4j
public class SessionCheckHandlerInterceptor implements HandlerInterceptor {

    private final UserValidator validator;
    private final UserLoginUseCase loginUseCase;

    public SessionCheckHandlerInterceptor(
        UserValidator validator,
        UserLoginUseCase loginUseCase
    ) {
        this.validator = validator;
        this.loginUseCase = loginUseCase;
    }

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        Long sessionId = getSessionId(request.getCookies());
        validator.validateSessionId(sessionId, response);

        Session findSession = loginUseCase.findSessionById(sessionId);
        log.info("Session:{}", findSession);
        validator.validateSession(findSession, response);

        LoginUser loginUser = new LoginUser(findSession);
        request.setAttribute("loginUser", loginUser);
        return false;
    }

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView
    ) {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        HandlerInterceptor.super.afterCompletion(request, response, handler);
    }
}
