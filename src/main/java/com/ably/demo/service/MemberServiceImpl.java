package com.ably.demo.service;

import com.ably.demo.dto.PasswordChangeRequestDto;
import com.ably.demo.errors.*;
import com.ably.demo.repository.MemberRepository;
import com.ably.demo.repository.SignUpRepository;
import com.ably.demo.utils.SMSUtils;
import com.ably.demo.vo.Member;
import com.ably.demo.vo.SignUp;
import com.ably.demo.vo.SignUpStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final SignUpRepository signUpRepository;
    private final PasswordEncoder bcryptEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, SignUpRepository signUpRepository, @Lazy PasswordEncoder bcryptEncoder) {
        this.memberRepository = memberRepository;
        this.signUpRepository = signUpRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails member = memberRepository.findByEmailOrPhoneNumber(email, email);
        if (member == null) {
            throw new UsernameNotFoundException("존재 하지않는 고객입니다.");
        }

        return member;
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
        SMSUtils smsUtils = SMSUtils.getInstance();
        String authCode = smsUtils.sendSMSAuthCode(phoneNumber);

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
    public SignUp verifyAuthCode(String signupSessionId, String authCode) throws SignupWrongAccessException, SignupInvalidAuthCodeException {

        SignUp checkSignUp = signUpRepository.findBySignupSessionId(signupSessionId);
        if(checkSignUp == null){
            throw new SignupWrongAccessException("잘못된 접근입니다.");
        } else if (!checkSignUp.getAuthCode().equals(authCode) ) {
            throw new SignupInvalidAuthCodeException("올바르지 않은 인증코드입니다.");
        }

        checkSignUp.setStatus(SignUpStatus.VERIFIED_AUTH_CODE);

        SignUp result = signUpRepository.save(checkSignUp);
        return result;
    }

    @Override
    public Member changePassword(PasswordChangeRequestDto passwordForm) throws NotExistMemberException, SignupWrongAccessException, SignupInvalidAuthCodeException {
        Member searchedMemberByEmail = memberRepository.findByEmail(passwordForm.getEmail());
        if (searchedMemberByEmail == null) {
            throw new NotExistMemberException("the email is already exist.");
        }

        SignUp searchedSignUp = signUpRepository.findBySignupSessionId(passwordForm.getSignupSessionId());
        if(searchedSignUp == null){
            throw new SignupWrongAccessException("잘못된 접근입니다.");
        } else if(!searchedSignUp.getAuthCode().equals(passwordForm.getAuthCode())){
            throw new SignupInvalidAuthCodeException("인증번호가 틀렸습니다.");
        } else if(searchedSignUp.getStatus() != SignUpStatus.VERIFIED_AUTH_CODE){
            throw new SignupInvalidAuthCodeException("인증이 완료되지 않았습니다.");
        }

        searchedMemberByEmail.setPassword(bcryptEncoder.encode(passwordForm.getPassword()));

        Member member = memberRepository.save(searchedMemberByEmail);
        return member;
    }

    @Override
    public Member getPersonalInfo() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = "";
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        Member member = memberRepository.findByEmail(email);
        return member;
    }
}
