package com.ably.demo.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class PageController {

    private StandardSessionIdGenerator sessionGenerator = new StandardSessionIdGenerator();

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/signIn")
    public String signIn(Model model) {
        return "signIn";
    }

    @GetMapping("/signUp")
    public String signUp(Model model) throws NoSuchAlgorithmException {
        String signupSessionId = sessionGenerator.generateSessionId();
        model.addAttribute("signupSessionId", signupSessionId);
        return "signUp";
    }

    @GetMapping("/member/myPage")
    public String personalInfo(Model model) {
        return "myPage";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {
        String signupSessionId = sessionGenerator.generateSessionId();
        model.addAttribute("signupSessionId", signupSessionId);
        return "changePassword";
    }
}
