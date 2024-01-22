package project.server.mvc.springframework.handler;//package project.server.mission.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static project.server.mvc.springframework.context.ApplicationContextProvider.getBean;
import project.server.mvc.servlet.Servlet;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.Request;
import project.server.mvc.servlet.Response;

public final class RequestHandler extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Servlet dispatcherServlet;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.dispatcherServlet = getBean("dispatcherServlet");
    }

    public void run() {
        log.info("Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
        ) {
            HttpServletRequest request = new Request(bufferedReader);
            HttpServletResponse response = new Response(out);
            dispatcherServlet.service(request, response);
        } catch (IOException exception) {
            log.error(exception.getMessage());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
