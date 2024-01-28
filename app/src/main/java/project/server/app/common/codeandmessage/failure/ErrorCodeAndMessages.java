package project.server.app.common.codeandmessage.failure;

import project.server.app.common.codeandmessage.ErrorCodeAndMessage;
import project.server.mvc.servlet.http.HttpStatus;

public enum ErrorCodeAndMessages implements ErrorCodeAndMessage {
    INVALID_SESSION(HttpStatus.UN_AUTHORIZED, "세션이 만료되었습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "올바른 값을 입력해주세요."),
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."),
    UN_AUTHORIZED(HttpStatus.UN_AUTHORIZED, "권한이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCodeAndMessages(
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
