package com.wondo.council.service.impl;

import com.wondo.council.domain.User;
import com.wondo.council.domain.enums.Role;
import com.wondo.council.domain.enums.UserIsMember;
import com.wondo.council.dto.TokenDto;
import com.wondo.council.dto.exception.user.DuplicateNicknameException;
import com.wondo.council.dto.exception.user.UserNotFoundException;
import com.wondo.council.dto.user.LoginRequestDto;
import com.wondo.council.dto.user.SignUpRequestDto;
import com.wondo.council.jwt.TokenProvider;
import com.wondo.council.repository.UserRepository;
import com.wondo.council.service.UserService;
import com.wondo.council.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static jdk.nashorn.internal.objects.Global.print;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
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
                .isMember(UserIsMember.WAIT)
                .build();
        // 문제 없으면 저장
        try {
            userRepository.save(user);
            log.info("회원가입이 완료되었습니다.");
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

    @Override
    public TokenDto doLogin(LoginRequestDto loginDto){
        UserIsMember status = userRepository.findByUsername(loginDto.getUsername()).get().getIsMember();
        if (status.equals(UserIsMember.WAIT)){
            log.error("승인 대기 중 입니다.");
            throw new AccessDeniedException("승인 대기 중 입니다.");
        } else if (status.equals(UserIsMember.REFUSAL)) {
            log.error("승인 거절되었습니다.");
            throw new AccessDeniedException("승인 거절되었습니다.");
        } else {
            log.info("가입 승인 확인");
            // 아이디와 비밀번호로 AuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPw());
            System.out.println("authenticationToken : " + authenticationToken);

            // CustomUserDetailsService의 loadByUserName실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("authentication : "+authentication);
            log.info("authentication : "+authentication);

            // 인증 정보 기반으로 JWT 토큰 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
            System.out.println("tokenDto : "+ tokenDto);

            // RefreshToken 저장
            Optional<User> entity = userRepository.findByUsername(authentication.getName());
            if (entity.isPresent()) {
                entity.get().saveToken(tokenDto.getRefreshToken());
                userRepository.save(entity.get());
            }

            return tokenDto;
        }

    }
    @Override
    public User getMyInfo(){
        return SecurityUtil.getCurrentUsername().flatMap(userRepository :: findByUsername).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void changeNickname(String nickname) {
        User user = getMyInfo();
        Boolean changeNick = checkNickname(nickname);
        if (!changeNick){
            user.changeNickname(nickname);
            userRepository.save(user);
        } else {
            log.info("중복된 닉네임 입니다.");
            // 중복된 닉네임을 처리할 때 발생하는 오류
            throw new DuplicateNicknameException();
        }

    }

    @Override
    public void changePw(String pw) {
        User user = getMyInfo();
        user.changePw(passwordEncoder.encode(pw));
        userRepository.save(user);
        log.info("패스워드 변경이 완료되었습니다.");
    }

}
