package project.server.app.common.login;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.Map;
import java.util.Optional;
import static java.util.UUID.randomUUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import project.server.app.core.web.user.application.service.SessionManager;
import project.server.mvc.springframework.annotation.Component;

@Slf4j
@Component
public class SessionStore implements SessionManager {

    private static final int FIFTEEN_MINUTES = 15;
    private static final Map<Long, Session> factory = new ConcurrentHashMap<>();

    @Override
    public Session createSession(Long userId) {
        String uuid = randomUUID().toString();
        LocalDateTime after15Minutes = now().plusMinutes(FIFTEEN_MINUTES);

        Session newSession = new Session(userId, uuid, after15Minutes);
        factory.put(userId, newSession);
        return newSession;
    }

    @Override
    public Optional<Session> findByUserId(Long userId) {
        return Optional.ofNullable(factory.get(userId));
    }
}
