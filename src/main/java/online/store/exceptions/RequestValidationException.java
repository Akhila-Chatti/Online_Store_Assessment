package online.store.exceptions;

import org.springframework.http.HttpStatus;

public class RequestValidationException extends RuntimeException {

    public RequestValidationException(String message, HttpStatus status) {
        super(message);

    }

}
