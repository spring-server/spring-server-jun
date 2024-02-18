package project.server.mvc.tomcat;

import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEndpoint<U> {

    private final ExecutorService executorService;

    public AbstractEndpoint(ExecutorService executorService) {
        this.executorService = executorService;
    }

    protected abstract boolean setSocketOptions(U socket);
}
