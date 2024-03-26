package project.server.app.core.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Long save(User user);

    Optional<User> findById(Long userId);

    void clear();

    boolean existsByName(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    void update(Long id, String password);

    void delete(User user);

    List<User> findAll();
}
