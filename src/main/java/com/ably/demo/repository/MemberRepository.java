package com.ably.demo.repository;

import com.ably.demo.vo.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Member findByPhoneNumber(String phoneNumber);
    Member findByEmailOrPhoneNumber(String email, String phoneNumber);
    Member findByEmailAndPhoneNumber(String email, String phoneNumber);
}
