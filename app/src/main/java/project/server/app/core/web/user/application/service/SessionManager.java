package project.server.app.core.web.user.application.service;

import project.server.app.common.login.Session;

public interface SessionManager {
    Session save(Long userId, Session session);

    Session createSession(Long userId);
}