package com.ably.demo.errors;

public class NotExistMemberException extends Exception {
    public NotExistMemberException(String s) {
        super(s);
    }
}
