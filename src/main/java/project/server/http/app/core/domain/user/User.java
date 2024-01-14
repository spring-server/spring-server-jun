package project.server.http.app.core.domain.user;

import lombok.Getter;

import java.util.Objects;

@Getter
public class User {

    private final Long id;
    private String name;
    private String password;

    public User(
        Long id,
        String name,
        String password
    ) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("id: %s, name: %s", id, name);
    }
}
