package project.server.mvc.springframework.handler;//package project.server.mission.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.Request;
import project.server.mvc.servlet.Response;
import project.server.mvc.servlet.Servlet;
import static project.server.mvc.springframework.context.ApplicationContextProvider.getBean;

@Slf4j
public final class RequestHandler extends Thread {

    private final Socket connection;
    private final Servlet dispatcherServlet;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.dispatcherServlet = getBean("dispatcherServlet");
    }

    @Override
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
