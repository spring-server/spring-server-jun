package project.server.app.core.web.user.application.service;

import lombok.extern.slf4j.Slf4j;
import project.server.app.common.login.Session;
import project.server.app.core.domain.user.User;
import project.server.app.core.domain.user.UserRepository;
import project.server.app.core.web.user.application.LoginUseCase;
import project.server.app.core.web.user.exception.UserNotFoundException;
import project.server.mvc.springframework.annotation.Service;

@Slf4j
@Service
public class LoginService implements LoginUseCase {

    private final SessionManager sessionManager;
    private final UserRepository userRepository;

    public LoginService(
        SessionManager sessionManager,
        UserRepository userRepository
    ) {
        this.sessionManager = sessionManager;
        this.userRepository = userRepository;
    }

    @Override
    public Session login(
        String username,
        String password
    ) {
        User findUser = userRepository.findByUsernameAndPassword(username, password)
            .orElseThrow(UserNotFoundException::new);
        return sessionManager.createSession(findUser.getId());
    }
}
