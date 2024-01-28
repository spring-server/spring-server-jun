package project.server.app.core.web.user.application.service;

import java.util.Optional;
import project.server.app.common.login.Session;

public interface SessionManager {
    Session createSession(Long userId);

    Optional<Session> findByUserId(Long userId);
}
