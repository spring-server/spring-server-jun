package project.server.mvc.tomcat;

import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEndpoint {

    private final ExecutorService executorService;

    public AbstractEndpoint(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
