package project.server.mvc.servlet.http;

import java.util.Arrays;
import java.util.function.Predicate;

public enum HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    DELETE,
    CONNECT,
    OPTIONS,
    TRACE,
    PATCH;

    public static HttpMethod findHttpMethod(String value) {
        return Arrays.stream(values())
            .filter(equalTo(value))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("올바른 HttpMethod를 입력해주세요."));
    }

    private static Predicate<HttpMethod> equalTo(String value) {
        return httpMethod -> httpMethod.name().equals(value);
    }

    public boolean isGet() {
        return this.equals(GET);
    }

    public boolean isPost() {
        return this.equals(POST);
    }
}
