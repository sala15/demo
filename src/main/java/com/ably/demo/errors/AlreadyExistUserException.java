package com.ably.demo.errors;

public class AlreadyExistUserException extends Exception {

    public AlreadyExistUserException(String message) {
        super(message);
    }
}
