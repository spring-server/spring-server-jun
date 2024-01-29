package project.server.app.test.unittest.user;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static project.server.app.common.fixture.user.LoginUserFixture.createLoginUser;
import project.server.app.common.login.LoginUser;
import project.server.app.common.login.Session;

@DisplayName("[UnitTest] 로그인 유저 단위 테스트")
class LoginUserUnitTest {

    @Test
    @DisplayName("올바른 정보가 입력되면 로그인 된 사용자 객체가 생성된다.")
    void loginUserCreateTest() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        Session session = new Session(userId, uuid.toString(), now);

        assertAll(
            () -> assertNotNull(createLoginUser(userId, uuid, now)),
            () -> new LoginUser(session)
        );
    }

    @Test
    @DisplayName("로그인 유저는 사용자 PK 값으로 객체를 비교한다.")
    void loginUserEqualsTest() {
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();

        assertAll(
            () -> assertEquals(createLoginUser(1L, uuid, now), createLoginUser(1L, uuid, now)),
            () -> assertNotEquals(createLoginUser(2L, uuid, now), createLoginUser(1L, uuid, now))
        );
    }

    @Test
    @DisplayName("세션이 올바르다면 올바른 사용자다.")
    void loginUserValidTest() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        Session session = new Session(userId, uuid.toString(), now.plusMinutes(15));

        LoginUser loginUser = new LoginUser(session);

        assertTrue(loginUser.isValid());
    }

    @Test
    @DisplayName("세션이 올바르지 않다면 올바른 사용자가 아니다.")
    void loginUserInValidTest() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now().minusDays(3);
        UUID uuid = UUID.randomUUID();
        Session session = new Session(userId, uuid.toString(), now);
        LoginUser loginUser = new LoginUser(session);

        assertFalse(loginUser.isValid());
    }
}
