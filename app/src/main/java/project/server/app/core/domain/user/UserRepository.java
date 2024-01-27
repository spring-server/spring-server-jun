package project.server.app.core.domain.user;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long userId);

    void clear();
}
