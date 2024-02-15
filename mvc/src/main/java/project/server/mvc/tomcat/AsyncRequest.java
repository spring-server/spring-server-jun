package project.server.mvc.tomcat;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import project.server.mvc.servlet.HttpServletRequest;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.Request;
import project.server.mvc.servlet.Response;
import project.server.mvc.servlet.http.HttpHeaders;
import project.server.mvc.servlet.http.RequestBody;
import project.server.mvc.servlet.http.RequestLine;
import static project.server.mvc.springframework.context.ApplicationContextProvider.getBean;
import project.server.mvc.springframework.web.servlet.DispatcherServlet;

@Slf4j
public class AsyncRequest implements Runnable {

    private static final int START_OFFSET = 0;
    private static final int START_LINE = 0;
    private static final String CARRIAGE_RETURN = "\r\n";

    private final SocketChannel socketChannel;
    private final ByteBuffer buffer;
    private final DispatcherServlet dispatcherServlet = getBean("dispatcherServlet");
    private HttpServletResponse response;

    public AsyncRequest(
        SocketChannel socketChannel,
        ByteBuffer buffer
    ) {
        this.socketChannel = socketChannel;
        this.buffer = buffer;
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

            try (SocketChannel channel = this.socketChannel) {
                HttpServletRequest request = createHttpServletRequest(lines, headerLines, requestBody);
                HttpServletResponse response = new Response(channel);
                this.response = response;
                dispatcherServlet.service(request, response);

                ByteBuffer headerBuffer = ByteBuffer.wrap(response.toString().getBytes(UTF_8));
                while (headerBuffer.hasRemaining()) {
                    channel.write(headerBuffer);
                }
            }
        } catch (Exception exception) {
            log.error("message: {}", exception.getMessage());
        }
    }

    private String getRequestBodyBuilder(
        String[] lines,
        int index
    ) {
        StringBuilder requestBodyBuilder = new StringBuilder();
        if (index < lines.length) {
            for (int subIndex = index + 1; subIndex < lines.length; subIndex++) {
                requestBodyBuilder.append(lines[subIndex])
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

    public HttpServletResponse getResponse() {
        return response;
    }
}
