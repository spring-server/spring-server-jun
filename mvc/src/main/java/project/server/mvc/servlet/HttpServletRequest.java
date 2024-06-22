package project.server.mvc.servlet;

import project.server.mvc.servlet.http.ContentType;
import project.server.mvc.servlet.http.Cookies;
import project.server.mvc.servlet.http.HttpMethod;
import project.server.mvc.servlet.http.RequestBody;
import project.server.mvc.servlet.http.RequestLine;

public interface HttpServletRequest extends ServletRequest {
    String getHttpVersion();

    String getHost();

    @Override
    ContentType getContentType();

    @Override
    String getContentTypeAsString();

    RequestLine getRequestLine();

    HttpMethod getMethod();

    String getRequestUri();

    RequestBody getRequestBody();

    Object getAttribute(String key);

    Cookies getCookies();

    String getHeader(String key);

    void setAttribute(String key, Object value);

    boolean isContentType(ContentType contentType);
}
