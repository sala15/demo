package com.ably.demo.errors;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceException extends Exception {
    private String code;
    private String path;

    public ServiceException(String code, String path, String message) {
        super(message);
        this.code = code;
        this.path = path;
    }
}
