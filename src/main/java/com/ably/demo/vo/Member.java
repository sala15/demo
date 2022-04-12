package com.ably.demo.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 1, max = 256)
    @Email
    @Column(name = "email", length=256, nullable=false, unique=true)
    private String email;

    @Size(min = 1, max = 256)
    @Column(name = "nickname", length=256, nullable=false, unique=false)
    private String nickname;

    @Size(min = 8)
    @Column(name = "password", nullable=false)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    private String password;

    @Size(min = 1, max = 256)
    @Column(name = "name", length=256, nullable=false)
    private String name;

    @Size(min = 1, max = 11)
    @NotEmpty
    @Pattern(regexp="(^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$)")
    @Column(name = "phoneNumber", length=11, nullable=false, unique=true)
    private String phoneNumber;

    @Column(name = "regDate")
    private Date regDate;

    @Builder
    public Member(String email, String nickname, String password, String name, String phoneNumber) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
