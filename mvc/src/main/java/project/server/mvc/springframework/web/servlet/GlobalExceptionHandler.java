package project.server.mvc.springframework.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import project.server.mvc.servlet.HttpServletResponse;
import project.server.mvc.servlet.http.HttpStatus;
import project.server.mvc.springframework.annotation.Component;
import project.server.mvc.springframework.exception.ErrorResponse;

@Slf4j
@Component
public class GlobalExceptionHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void resolveException(
        HttpServletResponse response,
        Exception exception
    ) throws JsonProcessingException {
        Throwable cause = exception.getCause();
        String stringJson = cause.toString();
        ErrorResponse errorResponse = objectMapper.readValue(stringJson, ErrorResponse.class);

        HttpStatus findStatus = HttpStatus.findByCode(errorResponse.getCode());
        response.setStatus(findStatus);
    }
}
