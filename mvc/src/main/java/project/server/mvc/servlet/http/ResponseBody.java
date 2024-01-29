package project.server.mvc.servlet.http;

import java.util.Objects;

public class ResponseBody {

    private static final String EMPTY_BODY = "";
    private String body;

    public ResponseBody() {
        this.body = EMPTY_BODY;
    }

    public ResponseBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
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
        return body;
    }
}
