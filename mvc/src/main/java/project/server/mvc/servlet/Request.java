package project.server.mvc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import project.server.mvc.servlet.http.HttpHeaders;
import project.server.mvc.servlet.http.HttpMethod;
import project.server.mvc.servlet.http.RequestBody;
import project.server.mvc.servlet.http.RequestLine;

public class Request implements HttpServletRequest {

    private static final String NULL_STRING = "";

    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final RequestBody requestBody;

    public Request(BufferedReader bufferedReader) throws IOException {
        this.requestLine = parseRequestLine(bufferedReader);
        this.headers = parseHttpHeaders(bufferedReader);
        this.requestBody = parseRequestBody(bufferedReader);
    }

    private RequestLine parseRequestLine(BufferedReader bufferedReader) throws IOException {
        return new RequestLine(bufferedReader.readLine());
    }

    private HttpHeaders parseHttpHeaders(BufferedReader bufferedReader) throws IOException {
        List<String> headerLines = new ArrayList<>();
        String headerLine = "";

        while (!NULL_STRING.equals(headerLine = bufferedReader.readLine())) {
            headerLines.add(headerLine);
        }
        return new HttpHeaders(headerLines);
    }

    private RequestBody parseRequestBody(BufferedReader bufferedReader) throws IOException {
        int contentLength = headers.getContentLength();
        if (contentLength == 0) {
            return null;
        }

        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);
        return new RequestBody(new String(buffer));
    }

    @Override
    public int getContentLength() {
        return headers.getContentLength();
    }

    @Override
    public String getContentType() {
        return requestLine.getContentType();
    }

    @Override
    public RequestLine getRequestLine() {
        return requestLine;
    }

    @Override
    public HttpMethod getMethod() {
        return this.requestLine.getHttpMethod();
    }

    @Override
    public String getRequestURI() {
        return requestLine.getRequestUrl();
    }

    @Override
    public RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    public String getAttribute(String key) {
        return requestBody.getAttribute(key);
    }

    @Override
    public String toString() {
        return String.format("%s%s\r\n%s", requestLine, headers, requestBody);
    }
}
