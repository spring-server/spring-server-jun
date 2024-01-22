package project.server.mvc.servlet.http;

import java.util.Objects;

public class HttpHeader {

    private final String name;
    private final String value;

    public HttpHeader(
        String name,
        String value
    ) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHeader that = (HttpHeader) o;
        return name.equals(that.name) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", name, value);
    }
}
