package project.server.app.core.domain.user;

import java.time.LocalDateTime;
import java.util.Objects;
import static project.server.app.core.domain.user.Deleted.FALSE;

public class User {

    private Long id;
    private Username username;
    private Password password;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Deleted deleted;

    public User() {
    }

    public User(
        String username,
        String password
    ) {
        this(null, username, password);
    }

    public User(
        Long id,
        String username,
        String password
    ) {
        this(id, username, password, LocalDateTime.now(), null, FALSE);
    }

    public User(
        Long id,
        String username,
        String password,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        Deleted deleted
    ) {
        this.id = id;
        this.username = new Username(username);
        this.password = new Password(password);
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.deleted = deleted;
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

    public String getPassword() {
        return password.value();
    }

    public Deleted getDeleted() {
        return deleted;
    }

    public boolean isAlreadyDeleted() {
        return this.deleted.equals(Deleted.TRUE);
    }

    public void delete(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
        this.deleted = Deleted.TRUE;
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
