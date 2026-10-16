package online.store.exceptions;

import java.util.List;

public class ExceptionResponse {


    private List<String> message;


    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
