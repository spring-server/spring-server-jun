package project.server.mvc.test.unittest.http;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.server.mvc.servlet.http.Cookies;

@DisplayName("[UnitTest] 쿠키 단위 테스트")
class CookiesUnitTest {

    @Test
    @DisplayName("쿠키를 초기화하면 빈 값이 나온다.")
    void cookiesInitTest() {
        Cookies cookies = new Cookies();
        assertTrue(cookies.isEmpty());
    }

    @Test
    @DisplayName("쿠키 목록에 key를 넣으면 value 값이 나온다.")
    void cookieGetValueTest() {
        Cookies cookies = new Cookies(List.of("name=value"));
        assertEquals("value", cookies.getValue("name").value());
    }
}
