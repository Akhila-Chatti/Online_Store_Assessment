package online.store.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ExceptionResponse> handleRequestValidation(RequestValidationException exception) {
        ExceptionResponse response=new ExceptionResponse();
        response.setMessage(java.util.Arrays.asList(exception.getMessage().split("\\|")));
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreditCardValidationException.class)
    public ResponseEntity<ExceptionResponse> handleCreditCardValidation(CreditCardValidationException exception) {
        ExceptionResponse response=new ExceptionResponse();
        response.setMessage(java.util.Arrays.asList(exception.getMessage().split("\\|")));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(ResourceNotFoundException exception) {
        ExceptionResponse response=new ExceptionResponse();
        response.setMessage(java.util.Arrays.asList(exception.getMessage().split("\\|")));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
