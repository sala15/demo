package com.ably.demo.controller;

import com.ably.demo.dto.SignUpRequestDto;
import com.ably.demo.errors.*;
import com.ably.demo.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signUp")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/sendAuthCode")
    public String sendAuthCode(String signupSessionId, String phoneNumber) throws AlreadyExistUserException, SMSException {
        if(signUpService.existMember(phoneNumber)){
            throw new AlreadyExistUserException("이미 존재하는 고객입니다.");
        }
        signUpService.sendAuthCode(signupSessionId, phoneNumber);
        return "OK";
    }

    @PostMapping("/verifyAuthCode")
    public String verifyAuthCode(String signupSessionId, String authCode) throws SignupInvalidAuthCodeException, SignupWrongAccessException {
        signUpService.verifyAuthCode(signupSessionId, authCode);
        return "OK";
    }

    @PostMapping("/signUp")
    public String signUp(SignUpRequestDto signUpForm) throws AlreadyExistUserException, SignupBadRequestException, SignupInvalidAuthCodeException, SignupWrongAccessException {
        signUpService.signUp(signUpForm);
        return "OK";
    }

}
