package project.server.mvc.servlet.http;

import java.net.URLConnection;
import java.util.Objects;
import static project.server.mvc.servlet.http.HttpMethod.findHttpMethod;
import static project.server.mvc.servlet.http.HttpVersion.findHttpVersion;

public class RequestLine {

    private static final int METHOD = 0;
    private static final int URI = 1;
    private static final int VERSION = 2;
    private static final String DELIMITER = " ";
    private static final String BASIC_PREFIX = "/";
    private static final String STATIC_HOME = "index.html";

    private final HttpMethod httpMethod;
    private final RequestUri requestUri;
    private final HttpVersion httpVersion;
    private String contentType;

    public RequestLine(String startLine) {
        String[] startLineArray = startLine.split(DELIMITER);
        this.httpMethod = findHttpMethod(startLineArray[METHOD]);
        this.requestUri = getRequestUri(startLine);
        this.httpVersion = findHttpVersion(startLineArray[VERSION]);
    }

    private RequestUri getRequestUri(String startLine) {
        String[] startLineArray = startLine.split(DELIMITER);
        RequestUri requestUri = getRequestUri(startLine, startLineArray);
        if (requestUri != null) {
            return requestUri;
        }
        return new RequestUri(startLineArray[URI]);
    }

    private RequestUri getRequestUri(
        String startLine,
        String[] startLineArray
    ) {
        String requestFile;
        if (!startLine.isEmpty()) {
            requestFile = getRequestFile(startLineArray, null);
            this.contentType = getContentType(requestFile);
            return new RequestUri(BASIC_PREFIX + requestFile);
        }
        return null;
    }

    private String getRequestFile(
        String[] startLineArray,
        String requestFile
    ) {
        if (isStaticResource(startLineArray)) {
            requestFile = startLineArray[URI].substring(1);
            if (requestFile.isEmpty()) {
                requestFile = STATIC_HOME;
            }
        }
        return requestFile;
    }

    private boolean isStaticResource(String[] startLineArray) {
        return startLineArray.length >= 2;
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

    public String getRequestUri() {
        return requestUri.url();
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        RequestLine that = (RequestLine) object;
        return httpMethod == that.httpMethod &&
            requestUri.equals(that.requestUri) &&
            httpVersion == that.httpVersion;
    }

    public String getHttpVersionAsString() {
        return httpVersion.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, httpVersion);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s\r\n", httpMethod, requestUri, httpVersion.getValue());
    }
}
