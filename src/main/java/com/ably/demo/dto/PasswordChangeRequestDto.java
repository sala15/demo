package com.ably.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PasswordChangeRequestDto {
    private String email;
    private String authCode;
    private String password;
    private String signupSessionId;
}
