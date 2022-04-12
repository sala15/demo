package com.ably.demo.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // User Authentication Error
    INVALID_USER("UA0001", "invalid user"),
    ACCOUNT_EXPIRE("UA0001", "account expired"),
    PASSWORD_EXPIRE("UA0001", "password expired"),
    DISABLED_USER("UA0001", "disabled user"),
    LOCKED_USER("UA0001", "locked user"),

    // Sign up Error
    SIGNUP_ALREADY_EXIST_USER("SU0001", "already exist user"),
    SIGNUP_BAD_REQUEST("SU0002", "bad request"),
    SIGNUP_INVALID_AUTHCODE("SU0003", "invalid authcode"),
    SIGNUP_WRONG_ACCESS("SU0004", "wrong access"),

    // SMS Error
    SMS_INVALID_NUMBER("SM0001", "invalid number"),
    SMS_NETWORK_ERROR("SM0002", "SMS network error"),

    // Unknown
    UNKNOWN("UN0001", "unknown error");

    private String code;
    private String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}