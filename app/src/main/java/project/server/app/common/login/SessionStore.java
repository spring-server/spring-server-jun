package project.server.app.common.login;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.Map;
import static java.util.UUID.randomUUID;
import java.util.concurrent.ConcurrentHashMap;
import project.server.app.core.web.user.application.service.SessionManager;
import project.server.mvc.springframework.annotation.Component;

@Component
public class SessionStore implements SessionManager {

    private static final int FIFTEEN_MINUTES = 15;
    private static final Map<Long, Session> factory = new ConcurrentHashMap<>();

    @Override
    public Session save(
        Long userId,
        Session session
    ) {
        factory.put(userId, session);
        return session;
    }

    @Override
    public Session createSession(Long userId) {
        String uuid = randomUUID().toString();
        LocalDateTime after30Minutes = now().plusMinutes(FIFTEEN_MINUTES);

        Session newSession = new Session(userId, uuid, after30Minutes);
        factory.put(userId, newSession);
        return newSession;
    }
}
