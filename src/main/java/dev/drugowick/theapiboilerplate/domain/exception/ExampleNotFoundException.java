package dev.drugowick.theapiboilerplate.domain.exception;

public class ExampleNotFoundException extends EntityNotFoundException {

    public ExampleNotFoundException(String message) {
        super(message);
    }

    public ExampleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
