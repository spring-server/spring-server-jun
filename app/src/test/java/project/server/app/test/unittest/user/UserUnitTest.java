package project.server.app.test.unittest.user;

import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static project.server.app.common.fixture.user.UserFixture.createUser;
import static project.server.app.core.domain.user.Deleted.FALSE;
import static project.server.app.core.domain.user.Deleted.TRUE;
import project.server.app.core.domain.user.User;

@DisplayName("[UnitTest] 사용자 단위 테스트")
class UserUnitTest {

    @Test
    @DisplayName("사용자 객체를 생성할 수 있다.")
    void userCreateTest() {
        assertNotNull(createUser());
    }

    @Test
    @DisplayName("사용자가 최초로 생성되면 삭제 칼럼 값이 FALSE다.")
    void userInitDeletedTest() {
        User user = createUser();

        assertEquals(FALSE, user.getDeleted());
    }

    @Test
    @DisplayName("사용자를 삭제하면 deleted 값이 TRUE가 된다.")
    void userDeleteTest() {
        User user = createUser();
        LocalDateTime now = LocalDateTime.now();
        user.delete(now);

        assertEquals(TRUE, user.getDeleted());
    }

    @Test
    @DisplayName("사용자를 삭제하면 최종 수정한 날짜가 현재로 바뀐다.")
    void userDeleteLastModifiedAtTest() {
        User user = createUser();
        LocalDateTime now = LocalDateTime.now();
        user.delete(now);

        assertEquals(now, user.getLastModifiedAt());
    }

    @Test
    @DisplayName("equals를 재정의 했을 때, 값이 같다면 같은 객체로 인식한다.")
    void userEqualsTest() {
        User user = new User(1L, "John-Wak", "Hello0145");
        User sameUser = new User(1L, "John-Wak", "Hello0145");

        assertEquals(user, sameUser);
    }

    @Test
    @DisplayName("equals를 재정의 했을 때, 값이 다르다면 다른 객체로 인식한다.")
    void differentUserEqualsTest() {
        User user = new User(2L, "John-Wak", "Hello0145");
        User differentUser = new User(3L, "John-Wak", "Hello0145");

        assertNotEquals(user, differentUser);
    }

    @Test
    @DisplayName("equals를 재정의 했을 때, 값이 존재하지 않는다면, NPE가 발생한다.")
    void idNullUserEqualsTest() {
        User user = new User("John-Wak", "Hello0145");
        User differentUser = new User("John-Wak", "Hello0145");

        assertThatThrownBy(() -> user.equals(differentUser))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("toString을 재정의하면, 재정의한 값이 나온다.")
    void toStringOverrideTest() {
        User user = createUser();

        String expected = String.format("id:%s, name:%s", user.getId(), user.getUsernameAsValue());
        assertEquals(expected, user.toString());
    }
}
