package project.server.app.test.integrationtest.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.service.UserService;
import project.server.app.test.integrationtest.IntegrationTestBase;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;

@DisplayName("[IntegrationTest]")
class UserSaveIntegrationTest extends IntegrationTestBase {

    private final UserService userService = getBean(UserService.class);

    @Test
    @DisplayName("사용자가 저장되면 PK가 생성된다.")
    void userSaveTest() {
        User newUser = new User("Steve-Jobs", "helloworld");
        User savedUser = userService.save(newUser);

        assertNotNull(savedUser.getId());
    }
}
