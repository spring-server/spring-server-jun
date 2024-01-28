package project.server.app.test.integrationtest.user;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.common.exception.BusinessException;
import project.server.app.core.domain.user.User;
import project.server.app.core.domain.user.UserRepository;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.service.UserService;
import project.server.app.core.web.user.exception.AlreadyRegisteredUserException;
import project.server.app.core.web.user.exception.DuplicatedUsernameException;
import project.server.app.core.web.user.persistence.UserPersistenceRepository;
import project.server.app.test.integrationtest.IntegrationTestBase;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;

@Slf4j
@DisplayName("[IntegrationTest] 사용자 저장 통합 테스트")
class UserSaveIntegrationTest extends IntegrationTestBase {

    private final UserSaveUseCase userSaveUseCase = getBean(UserService.class);
    private final UserRepository userRepository = getBean(UserPersistenceRepository.class);

    @Test
    @DisplayName("사용자가 저장되면 PK가 생성된다.")
    void userSaveTest() {
        User newUser = new User("Steve-Jobs", "helloworld");
        User savedUser = userSaveUseCase.save(newUser);

        assertNotNull(savedUser.getId());
    }

    @Test
    @DisplayName("사용자가 이미 저장 돼 있다면 AlreadyRegisteredUserException이 발생한다.")
    void userSaveFailureTest() {
        User newUser = new User("Steve-Jobs", "helloworld");
        userSaveUseCase.save(newUser);

        assertThatThrownBy(() -> userRepository.save(newUser))
            .isInstanceOf(BusinessException.class)
            .isExactlyInstanceOf(AlreadyRegisteredUserException.class)
            .hasMessage("이미 가입된 사용자 입니다.");
    }

    @Test
    @DisplayName("중복된 아이디로 가입을 시도하면 DuplicatedException이 발생한다.")
    void duplicatedUsernameSaveTest() {
        User newUser = new User("Steve-Jobs", "helloworld");
        User duplicatedUser = new User("Steve-Jobs", "helloworld");
        userSaveUseCase.save(newUser);

        assertThatThrownBy(() -> userSaveUseCase.save(duplicatedUser))
            .isInstanceOf(BusinessException.class)
            .isExactlyInstanceOf(DuplicatedUsernameException.class);
    }

    @Test
    @DisplayName("동시에 1_000 명의 사용자가 가입해도 PK값은 유일하다.")
    void userSaveSynchronizedTest() throws InterruptedException {
        int fixedUserCount = 1_000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(fixedUserCount);

        Set<Long> userIds = ConcurrentHashMap.newKeySet();
        for (int index = 1; index <= fixedUserCount; index++) {
            User newUser = new User("Username" + index, "Password" + index);
            executorService.submit(() -> {
                try {
                    User savedUser = userSaveUseCase.save(newUser);
                    userIds.add(savedUser.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        List<User> findAllUsers = userRepository.findAll();
        log.info("findAllUsers: {}명", findAllUsers.size());

        assertEquals(findAllUsers.size(), userIds.size());
    }
}
