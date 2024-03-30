package nvt.st.securityjwt.exception;

import nvt.st.securityjwt.payload.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ExceptionResponse> handleException(Exception exception, WebRequest webRequest, HttpStatus httpStatus){
        ExceptionResponse errorDetails = ExceptionResponse.builder()
            .code(String.valueOf(httpStatus.value()))
            .message(exception.getMessage())
            .path(webRequest.getDescription(true))
            .time(LocalDateTime.now()).build();
            return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<ExceptionResponse> handleResourceExistsException(ResourceExistsException exception, WebRequest webRequest) {
        return handleException(exception, webRequest, HttpStatus.CONFLICT);
    }













}
