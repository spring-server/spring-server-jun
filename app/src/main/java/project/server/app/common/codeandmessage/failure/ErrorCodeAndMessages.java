package project.server.app.common.codeandmessage.failure;

import project.server.app.common.codeandmessage.ErrorCodeAndMessage;
import project.server.mvc.tomcat.servlet.http.HttpStatus;

public enum ErrorCodeAndMessages implements ErrorCodeAndMessage {
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다.");

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
