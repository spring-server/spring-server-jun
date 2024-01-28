package project.server.mvc.tomcat;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.Request;
import project.server.mvc.servlet.Response;
import project.server.mvc.servlet.http.HttpHeaders;
import project.server.mvc.servlet.http.RequestBody;
import project.server.mvc.servlet.http.RequestLine;
import static project.server.mvc.springframework.context.ApplicationContextProvider.getBean;
import project.server.mvc.springframework.web.servlet.DispatcherServlet;

public class AsyncRequest implements Runnable {

    private static final int START_OFFSET = 0;
    private static final int START_LINE = 0;
    private static final String CARRIAGE_RETURN = "\r\n";

    private final SocketChannel socketChannel;
    private final ByteBuffer buffer;
    private final DispatcherServlet dispatcherServlet;

    public AsyncRequest(
        SocketChannel socketChannel,
        ByteBuffer buffer
    ) {
        this.socketChannel = socketChannel;
        this.buffer = buffer;
        this.dispatcherServlet = getBean("dispatcherServlet");
    }

    @Override
    public void run() {
        try {
            String httpMessage = new String(buffer.array(), START_OFFSET, buffer.limit());
            String[] lines = httpMessage.split(CARRIAGE_RETURN);

            int index = 1;
            List<String> headerLines = new ArrayList<>();
            while (index < lines.length && !lines[index].isEmpty()) {
                headerLines.add(lines[index]);
                index++;
            }

            String requestBody = getRequestBodyBuilder(lines, index);
            OutputStream outputStream = socketChannel.socket().getOutputStream();

            HttpServletRequest request = createHttpServletRequest(lines, headerLines, requestBody);
            HttpServletResponse response = new Response(socketChannel, outputStream);
            dispatcherServlet.service(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private String getRequestBodyBuilder(String[] lines, int index) {
        StringBuilder requestBodyBuilder = new StringBuilder();
        if (index < lines.length) {
            for (index++; index < lines.length; index++) {
                requestBodyBuilder.append(lines[index])
                    .append(CARRIAGE_RETURN);
            }
        }
        return requestBodyBuilder.toString();
    }

    private HttpServletRequest createHttpServletRequest(
        String[] lines,
        List<String> headerLines,
        String requestBody
    ) {
        return new Request(
            new RequestLine(lines[START_LINE]),
            new HttpHeaders(headerLines),
            new RequestBody(requestBody)
        );
    }
}
