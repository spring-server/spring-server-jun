package project.server.app.core.web.user.persistence;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import project.server.app.core.domain.user.User;
import project.server.app.core.domain.user.UserRepository;
import project.server.app.core.web.user.exception.AlreadyRegisteredUserException;
import static project.server.app.core.web.user.exception.UserErrorCodeAndMessage.ALREADY_SAVED_USER;
import project.server.mvc.springframework.annotation.Repository;

@Repository
public class UserPersistenceRepository implements UserRepository {

    private static final Boolean ALREADY_EXIST = TRUE;
    private static final Boolean NOT_FOUND = FALSE;

    private static final Map<Long, User> factory = new ConcurrentHashMap<>();
    private static final Lock lock = new ReentrantLock();
    private static final AtomicLong idGenerator = new AtomicLong(0L);

    @Override
    public User save(User user) {
        lock.lock();
        try {
            Long id = idGenerator.incrementAndGet();
            if (!user.isNew()) {
                throw new AlreadyRegisteredUserException();
            }
            user.registerId(id);
            factory.put(id, user);
        } finally {
            lock.unlock();
        }
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(factory.get(userId));
    }

    @Override
    public boolean existByName(String username) {
        User findUser = factory.values().stream()
            .filter(x -> x.getUsername().equals(username))
            .findAny()
            .orElseGet(() -> null);
        return findUser != null ? ALREADY_EXIST : NOT_FOUND;
    }

    @Override
    public List<User> findAll() {
        return factory.values().stream()
            .toList();
    }

    @Override
    public void clear() {
        factory.clear();
    }
}
