package project.server.mvc.servlet.http;

public class StatusLine {

    private static final String DEFAULT_PROTOCOL_VERSION = "1.1";
    private String protocolVersion;
    private HttpStatus httpStatus;

    public StatusLine(HttpStatus httpStatus) {
        this.protocolVersion = DEFAULT_PROTOCOL_VERSION;
        this.httpStatus = httpStatus;
    }

    public StatusLine(
        String protocolVersion,
        HttpStatus httpStatus
    ) {
        this.protocolVersion = protocolVersion;
        this.httpStatus = httpStatus;
    }

    public StatusLine() {
        this.protocolVersion = "";
        this.httpStatus = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusLine that = (StatusLine) o;
        return protocolVersion.equals(that.protocolVersion)
            && httpStatus == that.httpStatus;
    }

    @Override
    public String toString() {
        return String.format("%s\s%s\s%s\r\n", protocolVersion, httpStatus.getStatusCode(), httpStatus.getStatus());
    }
}
