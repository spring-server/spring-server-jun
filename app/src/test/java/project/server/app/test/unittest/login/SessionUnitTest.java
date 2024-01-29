package project.server.app.test.unittest.login;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.common.login.Session;

@DisplayName("[UnitTest] 세션 단위 테스트")
class SessionUnitTest {

    @Test
    @DisplayName("세션은 sessionId로 객체를 비교한다.")
    void test() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        Session session = new Session(userId, uuid.toString(), now);
        Session differentSession = new Session(userId, UUID.randomUUID().toString(), now);

        assertAll(
            () -> assertTrue(session.equals(session)),
            () -> assertFalse(session.equals(differentSession))
        );
    }
}
