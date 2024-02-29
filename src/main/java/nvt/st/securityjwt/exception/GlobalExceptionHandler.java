package nvt.st.securityjwt.exception;

import nvt.st.securityjwt.dto.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ExceptionResponseDTO> handleException(Exception exception, WebRequest webRequest, HttpStatus httpStatus){
            ExceptionResponseDTO errorDetails = new ExceptionResponseDTO();
            errorDetails.setErrorCode(String.valueOf(httpStatus.value()));
            errorDetails.setErrorMessage(exception.getMessage());
            errorDetails.setPath(webRequest.getDescription(true));
            errorDetails.setTime(LocalDateTime.now());
            return new ResponseEntity<>(errorDetails, httpStatus);
    }


}
