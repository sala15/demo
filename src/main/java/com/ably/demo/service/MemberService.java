package com.ably.demo.service;

import com.ably.demo.dto.PasswordChangeRequestDto;
import com.ably.demo.errors.*;
import com.ably.demo.vo.Member;
import com.ably.demo.vo.SignUp;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    Boolean existMember(String phoneNumber);
    SignUp sendAuthCode(String signupSessionId, String phoneNumber) throws SMSException;
    SignUp verifyAuthCode(String signupSessionId, String authCode) throws SignupWrongAccessException, SignupInvalidAuthCodeException;
    Member changePassword(PasswordChangeRequestDto passwordForm) throws NotExistMemberException, SignupWrongAccessException, SignupInvalidAuthCodeException;
    Member getPersonalInfo();


}



