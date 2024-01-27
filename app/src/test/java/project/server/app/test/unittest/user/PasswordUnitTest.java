package project.server.app.test.unittest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.app.core.domain.user.Password;

@DisplayName("[UnitTest] 패스워드 단위 테스트")
class PasswordUnitTest {

    @Test
    @DisplayName("equals를 재정의 했을 때, 값이 같다면 같은 객체로 인식한다.")
    void samePasswordEqualsTest() {
        Password password = new Password("Helloworld145");
        Password samePassword = new Password("Helloworld145");

        assertEquals(password, samePassword);
    }

    @Test
    @DisplayName("equals를 재정의 했을 때, 값이 다르다면 다른 객체로 인식한다.")
    void differentPasswordEqualsTest() {
        Password password = new Password("Helloworld145");
        Password differentPassword = new Password("145HelloWorld");

        assertNotEquals(password, differentPassword);
    }
}
