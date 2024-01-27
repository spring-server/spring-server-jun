package project.server.app.test.integrationtest.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.common.exception.BusinessException;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.service.UserService;
import project.server.app.core.web.user.exception.AlreadyRegisteredUserException;
import project.server.app.test.integrationtest.IntegrationTestBase;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;

@DisplayName("[IntegrationTest]")
class UserSaveIntegrationTest extends IntegrationTestBase {

    private final UserSaveUseCase userSaveUseCase = getBean(UserService.class);

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

        assertThatThrownBy(() -> userSaveUseCase.save(newUser))
            .isInstanceOf(BusinessException.class)
            .isExactlyInstanceOf(AlreadyRegisteredUserException.class)
            .hasMessage("이미 가입된 사용자 입니다.");
    }
}
