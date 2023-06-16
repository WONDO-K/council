package com.wondo.council.service;

import com.wondo.council.domain.User;
import com.wondo.council.dto.TokenDto;
import com.wondo.council.dto.user.LoginRequestDto;
import com.wondo.council.dto.user.SignUpRequestDto;

public interface UserService {

    public void signup(SignUpRequestDto dto);

    boolean checkId(String userid);

    boolean checkNickname(String nickname);

    boolean checkEmail(String email);

    boolean checkDong(int dong);

    boolean checkHo(int ho);

    TokenDto doLogin(LoginRequestDto requestDto);

    User getMyInfo();

    void changeNickname(String nickname);

    void changePw(String pw);
}
