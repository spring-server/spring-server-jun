package project.server.mvc.springframework.web.servlet;

import lombok.extern.slf4j.Slf4j;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.http.HttpStatus;
import project.server.mvc.springframework.annotation.Component;

@Slf4j
@Component
public class GlobalExceptionHandler {

    public void resolveException(
        HttpServletResponse response,
        Exception exception
    ) {
        Throwable cause = exception.getCause();
        HttpStatus findStatus = getHttpStatus(cause.getMessage());
        log.error("{code:{}, message:{}}", findStatus.getStatusCode(), cause.getMessage());
        response.setStatus(findStatus);
    }

    public HttpStatus getHttpStatus(String message) {
        if (message.equals("중복된 아이디 입니다.")) {
            return HttpStatus.BAD_REQUEST;
        }
        if (message.equals("올바른 값을 입력해주세요.")) {
            return HttpStatus.BAD_REQUEST;
        }
        if (message.equals("이미 가입된 사용자 입니다.")) {
            return HttpStatus.BAD_REQUEST;
        }
        if (message.equals("사용자를 찾을 수 없습니다.")) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
