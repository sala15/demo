package com.ably.demo.errors;

public class SignupBadRequestException extends SignupException {

    public SignupBadRequestException(String s) {
        super(s);
    }
}
