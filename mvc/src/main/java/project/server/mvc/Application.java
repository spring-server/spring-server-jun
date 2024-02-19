package project.server.mvc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import project.server.mvc.springframework.context.ApplicationContext;
import project.server.mvc.springframework.context.ApplicationContextProvider;

@Slf4j
public class Application {

    private final Tomcat tomcat;

    @SneakyThrows
    public Application(
        String packages,
        int port,
        int threadCount
    ) {
        initContext(packages);
        this.tomcat = new Tomcat(port, threadCount);
    }

    private void initContext(String packages) throws Exception {
        ApplicationContext context = new ApplicationContext(packages);
        ApplicationContextProvider provider = new ApplicationContextProvider();
        provider.setApplicationContext(context);
    }

    public void start() {
        tomcat.run();
    }
}
