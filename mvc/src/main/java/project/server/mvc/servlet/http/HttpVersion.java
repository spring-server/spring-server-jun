package project.server.mvc.servlet.http;

import java.util.Arrays;
import java.util.function.Predicate;

public enum HttpVersion {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1");

    private final String value;

    HttpVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HttpVersion findHttpVersion(String value) {
        return Arrays.stream(values())
            .filter(equalTo(value))
            .findAny()
            .orElseGet(() -> HTTP_1_1);
    }

    private static Predicate<HttpVersion> equalTo(String value) {
        return httpVersion -> httpVersion.value.equals(value);
    }
}
