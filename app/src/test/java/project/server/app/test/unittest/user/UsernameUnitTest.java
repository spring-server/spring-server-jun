package project.server.app.test.unittest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.core.domain.user.Username;

@DisplayName("[UnitTest] 사용자 이름 단위 테스트")
class UsernameUnitTest {

    @Test
    @DisplayName("equals를 재정의 했을 때, 값이 같다면 같은 객체로 인식한다.")
    void sameUsernameEqualsTest() {
        Username username = new Username("Steve-Jobs");
        Username sameUsername = new Username("Steve-Jobs");

        assertEquals(username, sameUsername);
    }

    @Test
    @DisplayName("equals를 재정의 했을 때, 값이 다르다면 다른 객체로 인식한다.")
    void differentUsernameEqualsTest() {
        Username username = new Username("Steve-Jobs");
        Username differentUsername = new Username("Jobs-Steve");

        assertNotEquals(username, differentUsername);
    }
}
