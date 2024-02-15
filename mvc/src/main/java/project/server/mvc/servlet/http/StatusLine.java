package project.server.mvc.servlet.http;

public class StatusLine {

    private static final String CARRIAGE_RETURN = "\r\n";
    private static final HttpVersion basicProtocolVersion = HttpVersion.HTTP_1_1;

    private String protocolVersion;
    private HttpStatus httpStatus;

    public StatusLine() {
        this.protocolVersion = basicProtocolVersion.getValue();
        this.httpStatus = HttpStatus.OK;
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

    public String getHttpStatusAsString() {
        return httpStatus.getStatus();
    }

    public void setStatus(HttpStatus status) {
        this.httpStatus = status;
    }

    @Override
    public String toString() {
        return String.format("%s %s", protocolVersion, httpStatus.getStatus()) + CARRIAGE_RETURN;
    }
}
