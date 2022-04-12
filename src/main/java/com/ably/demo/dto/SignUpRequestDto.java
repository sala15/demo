package com.ably.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SignUpRequestDto {
    private String email;
    private String nickname;
    private String password;
    private String name;
    private String phoneNumber;
    private String authCode;
    private String signupSessionId;
}
