package project.server.mvc.tomcat;

import java.util.concurrent.ExecutorService;

public abstract class AbstractJsseEndpoint<U> extends AbstractEndpoint<U> {

    public AbstractJsseEndpoint(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    abstract protected boolean setSocketOptions(U socket);
}
