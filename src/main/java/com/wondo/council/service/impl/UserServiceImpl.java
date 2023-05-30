package com.wondo.council.service.impl;

import com.wondo.council.domain.User;
import com.wondo.council.domain.enums.Role;
import com.wondo.council.dto.user.SignUpRequestDto;
import com.wondo.council.jwt.TokenProvider;
import com.wondo.council.repository.UserRepository;
import com.wondo.council.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    @Override
    public void signup(SignUpRequestDto dto){
        // User 클래스 틀에 맞춰 값 대입
        User user = User.builder()
                .username(dto.getId())
                .pw(passwordEncoder.encode(dto.getPw()))
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .role(Role.USER)
                .dong(dto.getDong())
                .ho(dto.getHo())
                .phone(dto.getPhone())
                .build();
        // 문제 없으면 저장
        try {
            userRepository.save(user);
            System.out.println("회원가입 저장 완료");
        // 문제 생기면 오류 발생
        } catch (DataIntegrityViolationException e){
            // DataIntegrityViolationException : 뭔가 잘못된 데이터가 바인딩 되었을때 발생하는 에러이다. SQL 문이 잘못되었거나 Data가 잘못되었을 경우
            e.printStackTrace();
        }

    }

    @Override
    public boolean checkId(String username) {
        // user id로 검색 후 존재유무를 bool값으로 전달
        Optional<User> entity = userRepository.findByUsername(username);
        return entity.isPresent();
    }

    @Override
    public boolean checkNickname(String nickname){
        // nickname으로 검색후 존재 유무를 bool값으로 전달
        Optional<User> entity = userRepository.findByNickname(nickname);
        return entity.isPresent();
    }

    @Override
    public boolean checkEmail(String email){
        // nickname으로 검색후 존재 유무를 bool값으로 전달
        Optional<User> entity = userRepository.findByEmail(email);
        return entity.isPresent();
    }

}
