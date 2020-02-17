package se.expleostockholm.signup.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) { super(message);
    }
}