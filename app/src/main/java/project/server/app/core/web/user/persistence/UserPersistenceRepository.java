package project.server.app.core.web.user.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import static project.server.app.common.codeandmessage.failure.UserErrorCodeAndMessage.ALREADY_SAVED_USER;
import project.server.app.core.domain.user.User;
import project.server.app.core.domain.user.UserRepository;
import project.server.app.common.exception.BusinessException;
import project.server.mvc.springframework.annotation.Repository;

@Repository
public class UserPersistenceRepository implements UserRepository {

    private static final Map<Long, User> factory = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(0L);

    @Override
    public User save(User user) {
        Long id = idGenerator.incrementAndGet();
        if (!user.isNew()) {
            throw new BusinessException(ALREADY_SAVED_USER);
        }
        user.registerId(id);
        factory.put(id, user);
        return user;
    }
}
