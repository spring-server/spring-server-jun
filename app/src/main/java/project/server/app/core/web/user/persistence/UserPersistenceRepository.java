package project.server.app.core.web.user.persistence;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.SneakyThrows;
import project.server.app.common.configuration.DatabaseConfiguration;
import project.server.app.core.domain.user.User;
import project.server.app.core.domain.user.UserRepository;
import project.server.jdbc.core.jdbc.JdbcTemplate;
import project.server.mvc.springframework.annotation.Repository;

@Repository
public class UserPersistenceRepository implements UserRepository {

    private static final Map<Long, User> factory = new ConcurrentHashMap<>();

    private final JdbcTemplate<User> jdbcTemplate;

    private UserPersistenceRepository(DatabaseConfiguration dbConfig) throws IOException {
        this.jdbcTemplate = dbConfig.jdbcTemplate();
    }

    @Override
    @SneakyThrows
    public User save(User user) {
        return jdbcTemplate.save(user);
    }

    @Override
    @SneakyThrows
    public Optional<User> findById(Long userId) {
        User findUser = jdbcTemplate.findById(User.class, userId);
        return Optional.ofNullable(findUser);
    }

    @Override
    @SneakyThrows
    public boolean existByName(String username) {
        return jdbcTemplate.existsByField(User.class, "username", username);
    }

    @Override
    @SneakyThrows
    public Optional<User> findByUsernameAndPassword(
        String username,
        String password
    ) {
        return jdbcTemplate.findByUsernameAndPassword(User.class, username, password);
    }

    @Override
    @SneakyThrows
    public List<User> findAll() {
        return jdbcTemplate.findAll(User.class);
    }

    @Override
    public void clear() {
        factory.clear();
    }
}
