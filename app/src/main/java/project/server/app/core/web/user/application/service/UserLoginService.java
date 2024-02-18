package project.server.app.core.web.user.application.service;

import static java.time.LocalDateTime.now;
import lombok.extern.slf4j.Slf4j;
import project.server.app.common.exception.SessionExpiredException;
import project.server.app.common.exception.UnAuthorizedException;
import project.server.app.common.login.Session;
import project.server.app.core.domain.user.User;
import project.server.app.core.domain.user.UserRepository;
import project.server.app.core.web.user.application.SessionManager;
import project.server.app.core.web.user.application.UserLoginUseCase;
import project.server.app.core.web.user.exception.UserNotFoundException;
import project.server.mvc.springframework.annotation.Service;

@Slf4j
@Service
public class UserLoginService implements UserLoginUseCase {

    private final SessionManager sessionManager;
    private final UserRepository userRepository;

    public UserLoginService(
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

    @Override
    public Session findSessionById(Long userId) {
        try {
            Session findSession = sessionManager.findByUserId(userId)
                .orElseThrow(UnAuthorizedException::new);

            if (!findSession.isValid(now())) {
                throw new SessionExpiredException();
            }
            return findSession;
        } catch (UnAuthorizedException | SessionExpiredException exception) {
            return null;
        }
    }
}
