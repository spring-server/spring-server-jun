package project.server.mvc.servlet.http;

public class StatusLine {

    private static final HttpVersion basicProtocolVersion = HttpVersion.HTTP_1_1;

    private String protocolVersion;
    private HttpStatus httpStatus;

    public StatusLine() {
        this.protocolVersion = basicProtocolVersion.getValue();
    }

    public StatusLine(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setStatus(HttpStatus status) {
        this.httpStatus = status;
    }

    public String getHttpStatusAsString() {
        return httpStatus.getStatus();
    }
}
