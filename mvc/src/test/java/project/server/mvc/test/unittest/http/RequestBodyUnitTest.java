package project.server.mvc.test.unittest.http;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static project.server.mvc.common.fixture.RequestBodyFixture.REQUEST_BODY;
import project.server.mvc.servlet.http.RequestBody;

@DisplayName("[UnitTest] 요청 바디 단위 테스트")
class RequestBodyUnitTest {

    @Test
    @DisplayName("바디가 올바르게 파싱되면 객체가 생성된다.")
    void requestBodyParseUnitTest() {
        assertNotNull(new RequestBody(REQUEST_BODY));
    }

    @Test
    @DisplayName("RequestBody에서 Username, Password를 얻을 수 있다.")
    void requestBodyGetAttributeUnitTest() {
        RequestBody requestBody = new RequestBody(REQUEST_BODY);
        assertAll(
            () -> assertEquals("Steve-Jobs", requestBody.getAttribute("username")),
            () -> assertEquals("HelloWorld", requestBody.getAttribute("password"))
        );
    }
}
