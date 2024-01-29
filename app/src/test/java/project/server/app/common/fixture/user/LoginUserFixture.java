package project.server.app.common.fixture.user;

import java.time.LocalDateTime;
import java.util.UUID;
import project.server.app.common.login.LoginUser;
import project.server.app.common.login.Session;

public final class LoginUserFixture {

    private LoginUserFixture() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    public static LoginUser createLoginUser(
        Long userId,
        UUID sessionId,
        LocalDateTime expiredAt
    ) {
        Session session = new Session(userId, sessionId.toString(), expiredAt);
        return new LoginUser(session);
    }
}
