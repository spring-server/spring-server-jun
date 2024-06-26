package project.server.app.core.web.user.application.service;

import java.time.LocalDateTime;
import project.server.app.common.login.LoginUser;
import project.server.app.core.domain.user.User;
import project.server.app.core.domain.user.UserRepository;
import project.server.app.core.web.user.application.UserDeleteUseCase;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.UserSearchUseCase;
import project.server.app.core.web.user.application.UserUpdateUseCase;
import project.server.app.core.web.user.exception.DuplicatedUsernameException;
import project.server.app.core.web.user.exception.UserNotFoundException;
import project.server.mvc.springframework.annotation.Service;

@Service
public class UserService implements UserSaveUseCase, UserSearchUseCase, UserUpdateUseCase, UserDeleteUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long save(
        String username,
        String password
    ) {
        User user = new User(username, password);
        boolean duplicatedUser = userRepository.existsByName(user.getUsername());
        if (duplicatedUser) {
            throw new DuplicatedUsernameException();
        }
        return userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        User findUser = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        if (findUser.isAlreadyDeleted()) {
            throw new UserNotFoundException();
        }
        return findUser;
    }

    @Override
    public void update(
        LoginUser loginUser,
        String password
    ) {
        User findUser = userRepository.findById(loginUser.getUserId())
            .orElseThrow(UserNotFoundException::new);
        userRepository.update(findUser.getId(), password);
    }

    @Override
    public void delete(LoginUser loginUser) {
        User findUser = userRepository.findById(loginUser.getUserId())
            .orElseThrow(UserNotFoundException::new);

        if (findUser.isAlreadyDeleted()) {
            throw new UserNotFoundException();
        }

        findUser.delete(LocalDateTime.now());
        userRepository.delete(findUser);
    }
}
