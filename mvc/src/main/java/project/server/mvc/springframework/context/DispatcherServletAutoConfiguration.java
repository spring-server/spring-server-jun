package project.server.mvc.springframework.context;

import project.server.mvc.springframework.annotation.Bean;
import project.server.mvc.springframework.annotation.Configuration;
import project.server.mvc.springframework.web.servlet.DispatcherServlet;

@Configuration
public class DispatcherServletAutoConfiguration {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
}
