package project.server.app.test.integrationtest.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.common.exception.UnAuthorizedException;
import project.server.app.common.login.Session;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserLoginUseCase;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.service.UserLoginService;
import project.server.app.core.web.user.application.service.UserService;
import project.server.app.test.integrationtest.IntegrationTestBase;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;

@DisplayName("[IntegrationTest] 로그인 통합 테스트")
class UserLoginIntegrationTest extends IntegrationTestBase {

    private UserSaveUseCase userSaveUseCase = getBean(UserService.class);
    private UserLoginUseCase loginUseCase = getBean(UserLoginService.class);

    @Test
    @DisplayName("정상적으로 로그인이 되면 세션이 발급된다.")
    void sessionCreateTest() {
        User savedUser = userSaveUseCase.save(new User("Steve-Jobs", "Helloworld"));
        Session session = loginUseCase.login(savedUser.getUsername(), savedUser.getPassword());

        assertNotNull(session);
    }

    @Test
    @DisplayName("세션이 존재하면 이를 조회할 수 있다.")
    void sessionSearchTest() {
        User newUser = new User("Steve-Jobs", "Helloworld");
        User savedUser = userSaveUseCase.save(newUser);
        Session session = loginUseCase.login(savedUser.getUsername(), savedUser.getPassword());

        assertNotNull(loginUseCase.findSessionById(session.getUserId()));
    }

    @Test
    @DisplayName("세션이 존재하지 않으면 UnAuthorizedException이 발생한다.")
    void sessionSearchFailureTest() {
        Long invalidSessionId = Long.MAX_VALUE;

        assertThatThrownBy(() -> loginUseCase.findSessionById(invalidSessionId))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(UnAuthorizedException.class)
            .hasMessage("권한이 존재하지 않습니다.");
    }
}
