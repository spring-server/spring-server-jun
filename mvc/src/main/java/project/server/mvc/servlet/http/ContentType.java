package project.server.mvc.servlet.http;

import java.util.Arrays;
import java.util.function.Predicate;

public enum ContentType {
    TEXT_HTML("html", "text/html"),
    TEXT_CSS("css", "text/css"),
    TEXT_JS("js", "text/javascript"),
    IMAGE_JPEG("jpeg", "image/jpeg"),
    IMAGE_PNG("png", "image/png"),
    APPLICATION_JSON("", "application/json"),
    APPLICATION_OCTET_STREAM("*", "application/octet-stream");

    private final String type;
    private final String value;

    ContentType(
        String type,
        String value
    ) {
        this.type = type;
        this.value = value;
    }

    public static ContentType findByType(String type) {
        return Arrays.stream(values())
            .filter(equals(type))
            .findAny()
            .orElseGet(() -> APPLICATION_OCTET_STREAM);
    }

    private static Predicate<ContentType> equals(String type) {
        return contentType -> contentType.value.equals(type);
    }

    public String getValue() {
        return value;
    }
}
