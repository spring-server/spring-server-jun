package project.server.app.core.domain.user;

import java.util.Objects;

public class User {

    private Long id;
    private Username username;
    private Password password;

    public User(
        String username,
        String password
    ) {
        this(null, username, password);
    }

    public User(
        Long id,
        String name,
        String password
    ) {
        this.id = id;
        this.username = new Username(name);
        this.password = new Password(password);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username.value();
    }

    public Username getUsernameAsValue() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public void registerId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof User user)) {
            return false;
        }
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("id:%s, name:%s", id, username);
    }
}
