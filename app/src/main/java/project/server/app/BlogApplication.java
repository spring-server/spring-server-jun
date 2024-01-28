package project.server.app;

import project.server.mvc.Application;
import static project.server.mvc.tomcat.PortFinder.findPort;

public class BlogApplication {

    public static void main(String[] args) {
        int port = findPort(args);
        Application application = new Application("project", port, 200);
        application.start();
    }
}
