package project.server.app.core.web.user.application;

import project.server.app.core.domain.user.User;

public interface UserSaveUseCase {
    User save(User user);
}
