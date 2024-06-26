package project.server.mvc.servlet;

import java.util.List;
import project.server.mvc.servlet.http.ContentType;
import project.server.mvc.servlet.http.Cookies;
import project.server.mvc.servlet.http.HttpHeader;
import project.server.mvc.servlet.http.HttpHeaders;
import project.server.mvc.servlet.http.HttpMethod;
import project.server.mvc.servlet.http.RequestBody;
import project.server.mvc.servlet.http.RequestLine;

public class Request implements HttpServletRequest {

    private static final String HOST = "Host";

    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final RequestBody requestBody;

    public Request(
        RequestLine requestLine,
        HttpHeaders headers,
        RequestBody requestBody
    ) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    @Override
    public String getHttpVersion() {
        return requestLine.getHttpVersionAsString();
    }

    @Override
    public String getHost() {
        List<HttpHeader> headers = this.headers.getValue(HOST);
        return !headers.isEmpty() ? headers.get(0).getValue() : null;
    }

    @Override
    public int getContentLength() {
        return headers.getContentLength();
    }

    @Override
    public ContentType getContentType() {
        return requestLine.getContentType();
    }

    @Override
    public String getContentTypeAsString() {
        return requestLine.getContentTypeAsValue();
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
    public String getRequestUri() {
        return requestLine.getRequestUri();
    }

    @Override
    public RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    public Object getAttribute(String key) {
        return requestBody.getAttribute(key);
    }

    @Override
    public Cookies getCookies() {
        return headers.getCookies();
    }

    @Override
    public String getHeader(String key) {
        return headers.getHeaderValue(key);
    }

    @Override
    public void setAttribute(
        String key,
        Object value
    ) {
        requestBody.setAttribute(key, value);
    }

    @Override
    public boolean isContentType(ContentType contentType) {
        return this.headers.isContentType(contentType);
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", requestLine, headers, requestBody);
    }
}
