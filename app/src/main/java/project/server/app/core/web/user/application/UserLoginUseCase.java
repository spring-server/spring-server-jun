package project.server.app.core.web.user.application;

import project.server.app.common.login.Session;

public interface UserLoginUseCase {
    Session login(String username, String password);

    Session findSessionById(Long userId);
}
