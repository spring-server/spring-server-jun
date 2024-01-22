package project.server.mvc.servlet.http;

import java.net.URLConnection;
import java.util.Objects;
import static project.server.mvc.servlet.http.HttpVersion.findHttpVersion;

public class RequestLine {

    private static final int METHOD = 0;
    private static final int URI = 1;
    private static final int VERSION = 2;
    private static final String DELIMITER = " ";
    private static final String STATIC_HOME = "index.html";

    private HttpMethod httpMethod;
    private RequestUri requestURI;
    private HttpVersion httpVersion;
    private String contentType;

    public RequestLine(String startLine) {
        String[] startLineArray = startLine.split(DELIMITER);
        this.httpMethod = HttpMethod.findHttpMethod(startLineArray[METHOD]);
        this.requestURI = getRequestUrl(startLine);
        this.httpVersion = findHttpVersion(startLineArray[VERSION]);
    }

    private RequestUri getRequestUrl(String startLine) {
        String[] startLineArray = startLine.split(DELIMITER);
        String requestFile = null;
        if (!startLine.isEmpty()) {
            if (startLineArray.length >= 2) {
                requestFile = startLineArray[1].substring(1);
                if (requestFile.isEmpty()) {
                    requestFile = STATIC_HOME;
                }
            }
            this.contentType = getContentType(requestFile);
            return new RequestUri(requestFile);
        }
        return new RequestUri(startLineArray[URI]);
    }

    private String getContentType(String filePath) {
        String contentType = URLConnection.guessContentTypeFromName(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public String getRequestUrl() {
        return requestURI.url();
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine requestLine = (RequestLine) o;
        return httpMethod == requestLine.httpMethod && httpVersion == requestLine.httpVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, httpVersion);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s\r\n", httpMethod, requestURI, httpVersion.getValue());
    }
}
