package project.server.app.core.web.user.application.service;

import project.server.app.core.domain.user.User;
import project.server.app.core.domain.user.UserRepository;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.mvc.springframework.annotation.Service;

@Service
public class UserService implements UserSaveUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
