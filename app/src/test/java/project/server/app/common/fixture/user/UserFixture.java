package project.server.app.common.fixture.user;

import project.server.app.core.domain.user.User;

public final class UserFixture {

    private UserFixture() {
        throw new AssertionError("올바른 방식으로 객체를 생성해주세요.");
    }

    public static User createUser() {
        return new User(
            1L,
            "Steve-Jobs",
            "HelloWorld145"
        );
    }
}
