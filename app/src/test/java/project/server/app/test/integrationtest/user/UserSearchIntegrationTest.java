package project.server.app.test.integrationtest.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.UserSearchUseCase;
import project.server.app.core.web.user.application.service.UserService;
import project.server.app.core.web.user.exception.UserNotFoundException;
import project.server.app.test.integrationtest.IntegrationTestBase;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;

@DisplayName("[IntegrationTest]")
class UserSearchIntegrationTest extends IntegrationTestBase {

    private final UserSaveUseCase userSaveUseCase = getBean(UserService.class);
    private final UserSearchUseCase userSearchUseCase = getBean(UserService.class);

    @Test
    @DisplayName("사용자가 저장되면 PK로 조회할 수 있다.")
    void userSearchTest() {
        User newUser = new User("Steve-Jobs", "helloworld");
        User savedUser = userSaveUseCase.save(newUser);

        User findUser = userSearchUseCase.findById(savedUser.getId());
        assertNotNull(findUser);
    }

    @Test
    @DisplayName("사용자가 존재하지 않으면 UserNotFoundException이 발생한다.")
    void userSearchNotFoundTest() {
        assertThatThrownBy(() -> userSearchUseCase.findById(Long.MAX_VALUE))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }
}
