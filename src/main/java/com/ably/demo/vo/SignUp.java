package com.ably.demo.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "signUp")
public class SignUp {

    @Id
    @Column(name = "signupSessionId", length = 32)
    private String signupSessionId;

    @Size(min = 1, max = 11)
    @Pattern(regexp="(^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$)")
    @Column(name = "phoneNumber", length=256, nullable=false)
    private String phoneNumber;

    @Column(name = "authCode", length = 6)
    private String authCode;

    @Column(name = "status")
    private SignUpStatus status;

    @Builder
    public SignUp(String signupSessionId, String phoneNumber, String authCode, SignUpStatus status) {
        this.signupSessionId = signupSessionId;
        this.phoneNumber = phoneNumber;
        this.authCode = authCode;
        this.status = status;
    }
}
