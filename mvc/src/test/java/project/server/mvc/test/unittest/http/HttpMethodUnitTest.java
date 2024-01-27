package project.server.mvc.test.unittest.http;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static project.server.mvc.servlet.http.HttpMethod.findHttpMethod;

@DisplayName("[UnitTest] HttpMethod 단위 테스트")
class HttpMethodUnitTest {

    @ParameterizedTest
    @ValueSource(strings = {"GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE", "PATCH"})
    @DisplayName("값이 올바르다면, 올바른 HTTP 메서드를 조회할 수 있다.")
    void findHttpMethodTest(String parameter) {
        assertNotNull(findHttpMethod(parameter));
    }

    @ParameterizedTest
    @ValueSource(strings = {"HELLO", "WORLD"})
    @DisplayName("값이 올바르지 않다면, IllegalArgumentException이 발생한다.")
    void httpMethodNotFoundTest(String parameter) {
        assertThatThrownBy(() -> findHttpMethod(parameter))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("올바른 HttpMethod를 입력해주세요.");
    }
}
