package project.server.app.test.unittest.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import project.server.app.common.exception.InvalidParameterException;
import project.server.app.common.exception.UnAuthorizedException;
import project.server.app.core.web.user.presentation.validator.UserValidator;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.Response;
import project.server.mvc.servlet.http.HttpStatus;

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
        HttpServletResponse response = new Response();
        assertThatThrownBy(() -> validator.validateSessionId(null, response))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(UnAuthorizedException.class);
    }

    @Test
    @DisplayName("사용자 아이디가 null이면 상태가 UnAuthorizedException이 된다.")
    void sessionIdNullStatusTest() {
        HttpServletResponse response = new Response();

        assertThrows(UnAuthorizedException.class, () -> {
                validator.validateSessionId(null, response);
            }
        );

        assertEquals(HttpStatus.UN_AUTHORIZED, response.getStatus());
    }

    @Test
    @DisplayName("사용자 아이디가 null이 아니라면 에러가 발생하지 않는다.")
    void sessionIdValidateTest() {
        HttpServletResponse response = new Response();
        assertDoesNotThrow(() -> validator.validateSessionId(1L, response));
    }

    @Test
    @DisplayName("세션이 존재하지 않으면 UnAuthorizedException이 발생한다.")
    void sessionNullTest() {
        HttpServletResponse response = new Response();
        assertThatThrownBy(() -> validator.validateSession(null, response))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(UnAuthorizedException.class);
    }
}
