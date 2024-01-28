package project.server.mvc.test.unittest.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static project.server.mvc.common.fixture.HeaderFixture.HTTP_REQUEST;
import project.server.mvc.servlet.http.HttpHeaders;

@DisplayName("[UnitTest] 요청 헤더 단위 테스트")
class RequestHeaderUnitTest {

    @Test
    @DisplayName("헤더가 올바르게 파싱되면 객체가 생성된다.")
    void requestHeadersParseTest() {
        assertNotNull(new HttpHeaders(parseHttpHeaders()));
    }

    @Test
    @DisplayName("헤더에 적절한 값이 있다면 값을 꺼낼 수 있다.")
    void requestHeadersGetValueTest() {
        HttpHeaders headers = new HttpHeaders(parseHttpHeaders());

        assertAll(
            () -> assertNotNull(headers.getValue("Accept")),
            () -> assertNotNull(headers.getValue("Accept-Encoding")),
            () -> assertNotNull(headers.getValue("Accept-Language")),
            () -> assertNotNull(headers.getValue("Connection")),
            () -> assertNotNull(headers.getValue("User-Agent"))
        );
    }

    @Test
    @DisplayName("헤더에 적절한 값이 없다면 null값을 반환한다.")
    void requestHeadersParseFailureTest() {
        HttpHeaders headers = new HttpHeaders(parseHttpHeaders());

        assertNull(headers.getValue("Hello-World"));
    }

    private List<String> parseHttpHeaders() {
        List<String> headerLines = new ArrayList<>();
        StringReader stringReader = new StringReader(HTTP_REQUEST);
        try (BufferedReader bufferedReader = new BufferedReader(stringReader)) {
            String headerLine;
            while ((headerLine = bufferedReader.readLine()) != null && !headerLine.isEmpty()) {
                headerLines.add(headerLine);
            }
        } catch (IOException exception) {
            throw new RuntimeException();
        }
        return headerLines;
    }
}
