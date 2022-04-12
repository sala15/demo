package com.ably.demo.repository;

import com.ably.demo.vo.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignUpRepository extends JpaRepository<SignUp, Long> {
    SignUp findBySignupSessionId(String signupSessionId);
}
