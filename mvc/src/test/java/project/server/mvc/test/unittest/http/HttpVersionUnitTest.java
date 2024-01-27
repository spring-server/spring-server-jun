package project.server.mvc.test.unittest.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static project.server.mvc.servlet.http.HttpVersion.HTTP_1_1;
import static project.server.mvc.servlet.http.HttpVersion.findHttpVersion;

@DisplayName("[UnitTest] HttpVersion 단위 테스트")
class HttpVersionUnitTest {

    @ParameterizedTest
    @ValueSource(strings = {"HTTP/1.0", "HTTP/1.1"})
    @DisplayName("값이 올바르다면, 올바른 HTTP 버전을 조회할 수 있다.")
    void findHttpVersionTest(String parameter) {
        assertNotNull(findHttpVersion(parameter));
    }

    @ParameterizedTest
    @ValueSource(strings = {"HTTP/2.2", "HTTP/3.9"})
    @DisplayName("값이 올바르지 않다면 HTTP/1.1이 조회된다.")
    void findBaseHttpVersionTest(String parameter) {
        assertEquals(HTTP_1_1, findHttpVersion(parameter));
    }
}
