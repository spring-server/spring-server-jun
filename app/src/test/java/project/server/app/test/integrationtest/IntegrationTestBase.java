package project.server.app.test.integrationtest;

import project.server.mvc.springframework.context.ApplicationContext;
import project.server.mvc.springframework.context.ApplicationContextProvider;

public abstract class IntegrationTestBase {

    protected ApplicationContext applicationContext;

    protected IntegrationTestBase() {
        try {
            applicationContext = new ApplicationContext("project");
            ApplicationContextProvider provider = new ApplicationContextProvider();
            provider.setApplicationContext(applicationContext);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
