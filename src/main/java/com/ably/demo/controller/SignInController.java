package com.ably.demo.controller;

import com.ably.demo.dto.PasswordChangeRequestDto;
import com.ably.demo.errors.NotExistMemberException;
import com.ably.demo.errors.SMSException;
import com.ably.demo.errors.SignupInvalidAuthCodeException;
import com.ably.demo.errors.SignupWrongAccessException;
import com.ably.demo.service.MemberService;
import com.ably.demo.vo.Member;
import com.ably.demo.vo.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signIn")
public class SignInController {

    private final MemberService memberService;

    @PostMapping("/changePassword/sendAuthCode")
    public String sendAuthCode(String signupSessionId, String phoneNumber) throws NotExistMemberException, SMSException {
        if(!memberService.existMember(phoneNumber)){
            throw new NotExistMemberException("존재하지 않는 고객입니다.");
        }

        SignUp signUp = memberService.sendAuthCode(signupSessionId, phoneNumber);
        return "OK";
    }

    @PostMapping("/changePassword")
    public String changePassword(PasswordChangeRequestDto passwordForm) throws SignupInvalidAuthCodeException, SignupWrongAccessException, NotExistMemberException {
        Member member = memberService.changePassword(passwordForm);
        return "OK";
    }

    @PostMapping("/changePassword/verifyAuthCode")
    public String verifyAuthCode(String signupSessionId, String authCode) throws SignupInvalidAuthCodeException, SignupWrongAccessException {
        SignUp signUp = memberService.verifyAuthCode(signupSessionId, authCode);
        return "OK";
    }


}
