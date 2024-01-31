package project.server.app.test.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static project.server.app.common.utils.HeaderUtils.getSessionId;
import project.server.mvc.servlet.http.Cookie;
import project.server.mvc.servlet.http.Cookies;

@DisplayName("[UnitTest] 헤더 유틸 단위 테스트")
class HeaderUtilsUnitTest {

    @Test
    @DisplayName("올바른 세션 아이디가 존재하면, 이 값은 Null이 아니다.")
    void validSessionIdGetTest() {
        Cookies cookies = new Cookies();
        cookies.add(new Cookie("sessionId", "1"));

        assertNotNull(getSessionId(cookies));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ㄹ", "ㄹ4", "ㅣ", "ㅏ", "z", "I"})
    @DisplayName("올바르지 않은 세션 아이디라면 null이 반환된다.")
    void invalidSessionIdGetTest(String parameter) {
        Cookies cookies = new Cookies();
        cookies.add(new Cookie("sessionId", parameter));

        assertNull(getSessionId(cookies));
    }
}
