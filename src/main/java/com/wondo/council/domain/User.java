package com.wondo.council.domain;

import com.wondo.council.domain.enums.Role;
import com.wondo.council.domain.enums.UserIsMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uid")
    private Long uid;

    @Column(name = "username")
    private String username;

    @Column(name = "user_pw")
    private String pw;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "role")
    private Role role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dong")
    private int dong;

    @Column(name = "ho")
    private int ho;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "isMember")
    private UserIsMember isMember;

    @Column(name = "token")
    private String token;

    public void saveToken(String token) {
        this.token = token;
    }
}
