package com.ably.demo.service;

import com.ably.demo.dto.SignUpRequestDto;
import com.ably.demo.errors.*;
import com.ably.demo.vo.Member;
import com.ably.demo.vo.SignUp;

public interface SignUpService {
    Member signUp(SignUpRequestDto member) throws AlreadyExistUserException, SignupBadRequestException, SignupWrongAccessException, SignupInvalidAuthCodeException;
    SignUp sendAuthCode(String signupSessionId, String phoneNumber) throws SMSException;
    Boolean existMember(String phoneNumber);
    SignUp verifyAuthCode(String signupSessionId, String authCode) throws SignupInvalidAuthCodeException, SignupWrongAccessException;
}



