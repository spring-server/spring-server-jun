package project.server.mvc.common.fixture;

public final class RequestLineFixture {

    private RequestLineFixture() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    public static final String GET_REQUEST_LINE = """
        GET /index.html HTTP/1.1\r
        Host: www.google.com\r
        \r
        """;
}
