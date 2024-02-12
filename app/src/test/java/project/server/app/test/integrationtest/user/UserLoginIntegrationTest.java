package project.server.app.test.integrationtest.user;

import static java.lang.Long.MAX_VALUE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.common.exception.UnAuthorizedException;
import project.server.app.common.login.Session;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserLoginUseCase;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.UserSearchUseCase;
import project.server.app.core.web.user.application.service.UserLoginServiceProxy;
import project.server.app.core.web.user.application.service.UserServiceProxy;
import project.server.app.test.integrationtest.IntegrationTestBase;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;

@DisplayName("[IntegrationTest] 로그인 통합 테스트")
class UserLoginIntegrationTest extends IntegrationTestBase {

    private final UserSaveUseCase userSaveUseCase = getBean(UserServiceProxy.class);
    private final UserSearchUseCase userSearchUseCase = getBean(UserServiceProxy.class);
    private final UserLoginUseCase loginUseCase = getBean(UserLoginServiceProxy.class);

    @Test
    @DisplayName("정상적으로 로그인이 되면 세션이 발급된다.")
    void sessionCreateTest() {
        Long userId = userSaveUseCase.save(new User("Steve-Jobs", "Helloworld"));
        User findUser = userSearchUseCase.findById(userId);
        Session session = loginUseCase.login(findUser.getUsername(), findUser.getPassword());

        assertNotNull(session);
    }

    @Test
    @DisplayName("세션이 존재하면 이를 조회할 수 있다.")
    void sessionSearchTest() {
        User newUser = new User("Steve-Jobs", "Helloworld");
        Long userId = userSaveUseCase.save(newUser);
        User findUser = userSearchUseCase.findById(userId);
        Session session = loginUseCase.login(findUser.getUsername(), findUser.getPassword());

        assertNotNull(loginUseCase.findSessionById(session.userId()));
    }

    @Test
    @DisplayName("세션이 존재하지 않으면 UnAuthorizedException이 발생한다.")
    void sessionSearchFailureTest() {
        Long invalidSessionId = MAX_VALUE;

        assertThatThrownBy(() -> loginUseCase.findSessionById(invalidSessionId))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(UnAuthorizedException.class)
            .hasMessage("권한이 존재하지 않습니다.");
    }
}
