package com.ably.demo.controller;

import com.ably.demo.dto.PersonalInfoDto;
import com.ably.demo.service.MemberService;
import com.ably.demo.vo.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/personalInfo")
    public PersonalInfoDto personalInfo(){
        Member member = memberService.getPersonalInfo();

        PersonalInfoDto personalInfo = PersonalInfoDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .build();

        return personalInfo;
    }
}
