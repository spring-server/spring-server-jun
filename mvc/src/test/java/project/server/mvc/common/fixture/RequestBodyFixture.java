package project.server.mvc.common.fixture;

public final class RequestBodyFixture {

    private RequestBodyFixture() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    public static final String REQUEST_BODY = "username=Steve-Jobs&password=HelloWorld";
}
