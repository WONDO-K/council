package com.wondo.council.domain;

import com.wondo.council.domain.enums.Role;
import com.wondo.council.domain.enums.UserIsMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    // List < 어떤 틀을 가짐? > 변수명
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Article> articles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reply> replies;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TradeLike> tradeLikes;

    @Column(name = "token")
    private String token;

    public void saveToken(String token) {
        this.token = token;
    }

    public void approvalSignUp(UserIsMember inputIsMember){
        this.isMember = inputIsMember;
    }

    public void changeNickname(String newNickname){
        this.nickname = newNickname;
    }

    public void changePw(String pw){
        this.pw = pw;
    }
}
