package project.server.mvc.servlet.http;

public record Cookie(
    String name,
    String value
) {

    @Override
    public String toString() {
        return String.format("name:%s=value:%s; ", name, value);
    }
}
