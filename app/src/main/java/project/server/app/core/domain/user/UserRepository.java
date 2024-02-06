package project.server.app.core.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long userId);

    void clear();

    boolean existByName(String username);

    List<User> findAll();

    Optional<User> findByUsernameAndPassword(String username, String password);

    void delete(User user);
}
