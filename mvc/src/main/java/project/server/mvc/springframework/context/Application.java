package project.server.mvc.springframework.context;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newFixedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.server.mvc.springframework.handler.RequestHandler;

public class Application {

    private static final int FIXED_THREAD_COUNTS = 32;
    private static final int MIN_PORT_NUMBER = 1;
    private static final int MAX_PORT_NUMBER = 65535;
    private static final int DEFAULT_PORT = 8085;

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final ExecutorService executors = newFixedThreadPool(FIXED_THREAD_COUNTS);

    static {
        ApplicationContext context = null;
        try {
            context = new ApplicationContext("project");
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        ApplicationContextProvider provider = new ApplicationContextProvider();
        provider.setApplicationContext(context);
    }

    public static void run(String[] args) {
        int port = findPort(args);
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                new RequestHandler(connection).start();
                executors.submit(new RequestHandler(connection));
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static int findPort(String[] args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }
        return parsePort(args);
    }

    private static int parsePort(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
                return DEFAULT_PORT;
            }
            return port;
        } catch (NumberFormatException exception) {
            return DEFAULT_PORT;
        }
    }
}
