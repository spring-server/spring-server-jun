package project.server.app.test.integrationtest.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.common.exception.BusinessException;
import static project.server.app.common.fixture.user.UserFixture.createUser;
import project.server.app.common.login.LoginUser;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserDeleteUseCase;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.UserSearchUseCase;
import project.server.app.core.web.user.application.service.UserService;
import project.server.app.core.web.user.exception.UserNotFoundException;
import project.server.app.test.integrationtest.IntegrationTestBase;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;

@DisplayName("[IntegrationTest] 사용자 삭제 통합 테스트")
class UserDeleteIntegrationTest extends IntegrationTestBase {

    private final UserSaveUseCase userSaveUseCase = getBean(UserService.class);
    private final UserSearchUseCase userSearchUseCase = getBean(UserService.class);
    private final UserDeleteUseCase userDeleteUseCase = getBean(UserService.class);

    @Test
    @DisplayName("삭제된 사용자를 삭제하려하면 UserNotFoundException이 발생한다.")
    void test() {
        User savedUser = userSaveUseCase.save(createUser());
        LoginUser loginUser = new LoginUser(savedUser.getId(), null);
        userDeleteUseCase.delete(loginUser);

        assertThatThrownBy(() -> userDeleteUseCase.delete(loginUser))
            .isInstanceOf(BusinessException.class)
            .isExactlyInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }
}