package com.ably.demo.errors;

public class SignupInvalidAuthCodeException extends SignupException {

    public SignupInvalidAuthCodeException(String s) {
        super(s);
    }
}
