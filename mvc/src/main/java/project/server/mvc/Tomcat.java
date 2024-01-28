package project.server.mvc;

import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newFixedThreadPool;
import project.server.mvc.tomcat.Nio2EndPoint;

public class Tomcat {

    private static final int DEFAULT_THREAD_COUNT = 200;
    private final Acceptor acceptor;

    public Tomcat(
        int port,
        Integer threadCount
    ) throws Exception {
        Nio2EndPoint nio2EndPoint = createNio2EndPoint(threadCount);
        this.acceptor = new Acceptor(port, nio2EndPoint);
    }

    private Nio2EndPoint createNio2EndPoint(Integer threadCount) {
        int tomcatThreadCount = getThreadCount(threadCount);
        ExecutorService executorService = newFixedThreadPool(tomcatThreadCount);
        return new Nio2EndPoint(executorService);
    }

    private int getThreadCount(Integer threadCount) {
        if (threadCount == null || threadCount == 0) {
            return DEFAULT_THREAD_COUNT;
        }
        if (threadCount < 0) {
            throw new IllegalArgumentException("올바른 쓰레드 개수를 넣어주세요.");
        }
        return threadCount;
    }

    public void run() {
        acceptor.run();
    }
}
