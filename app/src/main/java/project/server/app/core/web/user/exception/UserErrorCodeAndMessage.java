package project.server.app.core.web.user.exception;

import project.server.app.common.codeandmessage.ErrorCodeAndMessage;
import project.server.mvc.servlet.http.HttpStatus;

public enum UserErrorCodeAndMessage implements ErrorCodeAndMessage {
    ALREADY_SAVED_USER(HttpStatus.BAD_REQUEST, "이미 가입된 사용자 입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    UserErrorCodeAndMessage(
        HttpStatus httpStatus,
        String errorMessage
    ) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
