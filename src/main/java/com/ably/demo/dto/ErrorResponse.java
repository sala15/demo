package com.ably.demo.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorResponse {
    private Error error;

    public ErrorResponse(
            Error error
    ) {
        this.error = error;
    }
}