package project.server.mvc.test.unittest.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static project.server.mvc.common.fixture.RequestLineFixture.GET_REQUEST_LINE;
import static project.server.mvc.servlet.http.HttpMethod.GET;
import static project.server.mvc.servlet.http.HttpVersion.HTTP_1_1;
import project.server.mvc.servlet.http.RequestLine;

@DisplayName("[UnitTest] 요청 라인 단위 테스트")
class RequestLineUnitTest {

    @Test
    @DisplayName("RequestLine을 파싱하면 HttpMethod, URI, Content-Type, HTTP Version 정보를 얻을 수 있다.")
    void requestLineParseTest() throws IOException {
        StringReader stringReader = new StringReader(GET_REQUEST_LINE);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        RequestLine requestLine = new RequestLine(bufferedReader.readLine());

        assertAll(
            () -> assertEquals(GET, requestLine.getHttpMethod()),
            () -> assertEquals("/index.html", requestLine.getRequestUri()),
            () -> assertEquals("text/html", requestLine.getContentTypeAsValue()),
            () -> assertEquals(HTTP_1_1.getValue(), "HTTP/1.1")
        );
    }
}
