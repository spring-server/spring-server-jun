package project.server.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public final class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.info("Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (
                InputStream ignored = connection.getInputStream();
                OutputStream out = connection.getOutputStream()
        ) {
            InputStream fis = getClass().getClassLoader().getResourceAsStream("index.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            String index = stringBuilder.toString();
            byte[] buffer = index.getBytes(StandardCharsets.UTF_8);

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, buffer.length);
            responseBody(dos, buffer);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(
            DataOutputStream dos,
            int lengthOfBodyContent
    ) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(
            DataOutputStream dos,
            byte[] body
    ) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
