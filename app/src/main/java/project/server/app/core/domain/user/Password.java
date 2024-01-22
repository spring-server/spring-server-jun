package project.server.app.core.domain.user;

public record Password(String value) {

    @Override
    public String toString() {
        return value;
    }
}
