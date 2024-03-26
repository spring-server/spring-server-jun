package project.server.app.common.configuration;

import project.server.app.common.configuration.interceptor.SessionCheckHandlerInterceptor;
import project.server.app.core.web.user.application.UserLoginUseCase;
import project.server.app.core.web.user.presentation.validator.UserValidator;
import project.server.mvc.springframework.annotation.Component;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;
import static project.server.mvc.springframework.context.ApplicationContext.register;
import project.server.mvc.springframework.web.InterceptorRegistry;
import project.server.mvc.springframework.web.WebMvcConfigurer;

@Component
public class WebConfiguration implements WebMvcConfigurer {

    private final UserValidator validator;
    private final UserLoginUseCase loginUseCase;

    public WebConfiguration(
        UserValidator validator,
        UserLoginUseCase loginUseCase
    ) {
        this.validator = validator;
        this.loginUseCase = loginUseCase;
        register(new InterceptorRegistry());
        addInterceptors(getBean(InterceptorRegistry.class));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionCheckHandlerInterceptor(validator, loginUseCase))
            .addPathPatterns("/my-info.html", "/my-info", "/api/users");
    }
}
