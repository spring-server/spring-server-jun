package project.server.mvc.servlet;

import project.server.mvc.servlet.http.ContentType;

public interface ServletRequest {
    int getContentLength();

    ContentType getContentType();

    String getContentTypeAsString();
}
