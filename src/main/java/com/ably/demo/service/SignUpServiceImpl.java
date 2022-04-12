package com.ably.demo.service;

import com.ably.demo.dto.SignUpRequestDto;
import com.ably.demo.errors.*;
import com.ably.demo.repository.MemberRepository;
import com.ably.demo.repository.SignUpRepository;
import com.ably.demo.utils.SMSUtils;
import com.ably.demo.vo.Member;
import com.ably.demo.vo.SignUp;
import com.ably.demo.vo.SignUpStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Random;

//@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {

    private final MemberRepository memberRepository;
    private final SignUpRepository signUpRepository;
    private final PasswordEncoder bcryptEncoder;
    private final Random rand;

    public SignUpServiceImpl(MemberRepository memberRepository, SignUpRepository signUpRepository, @Lazy PasswordEncoder bcryptEncoder) {
        this.memberRepository = memberRepository;
        this.signUpRepository = signUpRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.rand = new Random();
    }

    @Override
    @Transactional
    public Member signUp(SignUpRequestDto signUpForm) throws AlreadyExistUserException, SignupBadRequestException, SignupWrongAccessException, SignupInvalidAuthCodeException {
        Member searchedMemberByEmail = memberRepository.findByEmail(signUpForm.getEmail());
        Member searchedMemberByPhoneNumber = memberRepository.findByPhoneNumber(signUpForm.getPhoneNumber());
        if (searchedMemberByEmail != null) {
            throw new AlreadyExistUserException("the email is already exist.");
        }

        if (searchedMemberByPhoneNumber != null) {
            throw new AlreadyExistUserException("the phoneNumber is already exist.");
        }

        SignUp searchedSignUp = signUpRepository.findBySignupSessionId(signUpForm.getSignupSessionId());
        if(searchedSignUp == null){
            throw new SignupWrongAccessException("잘못된 접근입니다.");
        } else if(!searchedSignUp.getAuthCode().equals(signUpForm.getAuthCode())){
            throw new SignupInvalidAuthCodeException("인증번호가 틀렸습니다.");
        } else if(searchedSignUp.getStatus() != SignUpStatus.VERIFIED_AUTH_CODE){
            throw new SignupInvalidAuthCodeException("인증이 완료되지 않았습니다.");
        }

        searchedSignUp.setStatus(SignUpStatus.DONE);

        signUpRepository.save(searchedSignUp);

        Member member = Member.builder()
                .email(signUpForm.getEmail())
                .password(bcryptEncoder.encode(signUpForm.getPassword()))
                .phoneNumber(signUpForm.getPhoneNumber())
                .nickname(signUpForm.getNickname())
                .name(signUpForm.getName())
                .build();

        try {
            return memberRepository.save(member);
        } catch (ConstraintViolationException e) {
            throw new SignupBadRequestException("요청된 정보가 올바르지 않습니다.");
        }
    }

    @Override
    public Boolean existMember(String phoneNumber) {
        Member member = memberRepository.findByPhoneNumber(phoneNumber);
        if (member != null) {
            return true;
        }

        return false;
    }

    @Override
    public SignUp sendAuthCode(String signupSessionId, String phoneNumber) throws SMSException {
        String authCode = SMSUtils.getInstance().sendSMSAuthCode(phoneNumber);

        SignUp signUp = SignUp.builder()
                .signupSessionId(signupSessionId)
                .phoneNumber(phoneNumber)
                .authCode(authCode)
                .status(SignUpStatus.SENT_AUTH_CODE)
                .build();

        SignUp result = signUpRepository.save(signUp);

        return result;
    }


    @Override
    public SignUp verifyAuthCode(String signupSessionId, String authCode) throws SignupInvalidAuthCodeException, SignupWrongAccessException {

        SignUp checkSignUp = signUpRepository.findBySignupSessionId(signupSessionId);
        if(checkSignUp == null){
            throw new SignupWrongAccessException("잘못된 접근입니다.");
        } else if (!checkSignUp.getAuthCode().equals(authCode) ) {
            throw new SignupInvalidAuthCodeException("인증번호가 틀렸습니다.");
        }

        checkSignUp.setStatus(SignUpStatus.VERIFIED_AUTH_CODE);

        SignUp result = signUpRepository.save(checkSignUp);
        return result;
    }
}
