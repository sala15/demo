package com.ably.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ChangePasswordDto {
    private String email;
    private String phoneNumber;
    private String password;
    private String authNo;
}
