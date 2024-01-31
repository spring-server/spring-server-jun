package project.server.app;

import project.server.mvc.Application;
import static project.server.mvc.tomcat.PortFinder.findPort;

public class BlogApplication {

    public static void main(String[] args) {
        String packages = "project";
        final int port = findPort(args);
        final int tomcatThreadCount = 200;
        Application application = new Application(packages, port, tomcatThreadCount);
        application.start();
    }
}
