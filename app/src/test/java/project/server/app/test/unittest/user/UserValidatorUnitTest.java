package project.server.app.test.unittest.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import project.server.app.common.exception.InvalidParameterException;
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
        assertThatThrownBy(() -> validator.validateSignUp(parameter, "helloworld"))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(InvalidParameterException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자 비밀번호가 Null 또는 빈 값이면 InvalidParameterException이 발생한다.")
    void passwordNullOrBlank(String parameter) {
        assertThatThrownBy(() -> validator.validateSignUp("Steve-Jobs", parameter))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(InvalidParameterException.class);
    }
}
