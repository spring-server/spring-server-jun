package project.server.mvc.servlet;

import project.server.mvc.servlet.http.HttpMethod;
import project.server.mvc.servlet.http.RequestBody;
import project.server.mvc.servlet.http.RequestLine;

public interface HttpServletRequest extends ServletRequest {
    String getHttpVersion();

    String getHost();

    @Override
    String getContentType();

    RequestLine getRequestLine();

    HttpMethod getMethod();

    String getRequestUri();

    RequestBody getRequestBody();

    String getAttribute(String key);
}
