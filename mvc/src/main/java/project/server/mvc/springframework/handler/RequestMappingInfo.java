package project.server.mvc.springframework.handler;

import java.util.Objects;
import project.server.mvc.servlet.http.HttpMethod;

public record RequestMappingInfo(
    HttpMethod httpMethod,
    String url
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMappingInfo that = (RequestMappingInfo) o;
        return httpMethod == that.httpMethod && url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, url);
    }

    @Override
    public String toString() {
        return String.format("httpMethod: %s, url: %s", httpMethod, url);
    }
}
