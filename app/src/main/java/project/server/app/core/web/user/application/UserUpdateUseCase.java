package project.server.app.core.web.user.application;

import project.server.app.common.login.LoginUser;

public interface UserUpdateUseCase {
    void update(LoginUser loginUser, String password);
}
