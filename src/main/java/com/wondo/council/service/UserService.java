package com.wondo.council.service;

import com.wondo.council.dto.user.SignUpRequestDto;

public interface UserService {

    public void signup(SignUpRequestDto dto);

    boolean checkId(String userid);

    boolean checkNickname(String nickname);

    boolean checkEmail(String email);
}
