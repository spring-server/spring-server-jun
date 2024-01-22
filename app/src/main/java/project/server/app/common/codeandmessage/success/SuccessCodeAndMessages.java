package project.server.app.common.codeandmessage.success;

import project.server.app.common.codeandmessage.SuccessCodeAndMessage;
import project.server.mvc.tomcat.servlet.http.HttpStatus;

public enum SuccessCodeAndMessages implements SuccessCodeAndMessage {
    OK(HttpStatus.OK, "OK");

    private final HttpStatus httpStatus;
    private final String message;

    SuccessCodeAndMessages(
        HttpStatus httpStatus,
        String message
    ) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
