package project.server.app.test.integrationtest.user;

import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.common.exception.BusinessException;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.service.UserServiceProxy;
import project.server.app.core.web.user.exception.DuplicatedUsernameException;
import project.server.app.test.integrationtest.IntegrationTestBase;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;

@Slf4j
@DisplayName("[IntegrationTest] 사용자 저장 통합 테스트")
class UserSaveIntegrationTest extends IntegrationTestBase {

    private final UserSaveUseCase userSaveUseCase = getBean(UserServiceProxy.class);

    @Test
    @DisplayName("사용자가 저장되면 PK가 생성된다.")
    void userSaveTest() {
        User newUser = new User("Steve-Jobs", "helloworld");
        Long userId = userSaveUseCase.save(newUser);

        assertNotNull(userId);
    }

    @Test
    @DisplayName("사용자가 이미 저장 돼 있다면 AlreadyRegisteredUserException이 발생한다.")
    void userSaveFailureTest() {
        User newUser = new User("Steve-Jobs", "helloworld");
        userSaveUseCase.save(newUser);

        assertThatThrownBy(() -> userSaveUseCase.save(newUser))
            .isInstanceOf(BusinessException.class)
            .isExactlyInstanceOf(DuplicatedUsernameException.class)
            .hasMessage("중복된 아이디 입니다.");
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
}
