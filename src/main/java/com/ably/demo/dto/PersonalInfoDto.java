package com.ably.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonalInfoDto {
    private String email;
    private String nickname;
    private String name;
    private String phoneNumber;
}
