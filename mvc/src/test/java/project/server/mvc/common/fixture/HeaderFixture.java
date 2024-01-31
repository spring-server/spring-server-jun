package project.server.mvc.common.fixture;

public final class HeaderFixture {

    private HeaderFixture() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    public static final String HTTP_REQUEST = "Host: www.google.com\n"
        + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36\n"
        + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n"
        + "Accept-Language: en-US,en;q=0.5\n"
        + "Accept-Encoding: gzip, deflate, br\n"
        + "Connection: keep-alive\n";
}
