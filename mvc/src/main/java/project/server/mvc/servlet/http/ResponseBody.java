package project.server.mvc.servlet.http;

import java.util.Objects;

public class ResponseBody {

    private static final String EMPTY_BODY = "";
    private static final String CARRIAGE_RETURN = "\r\n";

    private String body;

    public ResponseBody() {
        this.body = EMPTY_BODY;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ResponseBody that = (ResponseBody) object;
        return body.equals(that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public String toString() {
        if (body.isBlank()) {
            return "";
        }
        return body + CARRIAGE_RETURN;
    }
}
