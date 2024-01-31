package project.server.app.test.unittest.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import project.server.app.common.exception.InvalidParameterException;
import project.server.app.common.exception.UnAuthorizedException;
import project.server.app.core.web.user.presentation.validator.UserValidator;

@DisplayName("[UnitTest] 사용자 검증 단위 테스트")
class UserValidatorUnitTest {

    private UserValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserValidator();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자 이름이 Null 또는 빈 값이면 InvalidParameterException이 발생한다.")
    void usernameNullOrBlank(String parameter) {
        assertThatThrownBy(() -> validator.validateSignUpInfo(parameter, "helloworld"))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(InvalidParameterException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자 비밀번호가 Null 또는 빈 값이면 InvalidParameterException이 발생한다.")
    void passwordNullOrBlank(String parameter) {
        assertThatThrownBy(() -> validator.validateSignUpInfo("Steve-Jobs", parameter))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(InvalidParameterException.class);
    }

    @Test
    @DisplayName("사용자 아이디가 null이면 UnAuthorizedException이 발생한다.")
    void sessionIdNullTest() {
        assertThatThrownBy(() -> validator.validateSessionId(null))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(UnAuthorizedException.class);
    }

    @Test
    @DisplayName("사용자 아이디가 null이 아니라면 에러가 발생하지 않는다.")
    void sessionIdValidateTest() {
        assertDoesNotThrow(() -> validator.validateSessionId(1L));
    }
}
