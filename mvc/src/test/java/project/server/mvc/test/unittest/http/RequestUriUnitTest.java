package project.server.mvc.test.unittest.http;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import project.server.mvc.servlet.http.RequestUri;

@DisplayName("[UnitTest] RequestUri 단위 테스트")
class RequestUriUnitTest {

    @ParameterizedTest
    @ValueSource(strings = {"/", "/index.html"})
    @DisplayName("올바른 URI 값이 들어오면 객체가 생성된다.")
    void requestUriCreateTest(String parameter) {
        assertNotNull(new RequestUri(parameter));
    }

    @NullSource
    @ParameterizedTest
    @DisplayName("Null 값이 들어오면 IllegalArgumentException이 발생한다.")
    void requestUriCreateFailureTest(String parameter) {
        assertThatThrownBy(() -> new RequestUri(parameter))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("올바른 URL을 입력해주세요.");
    }
}
