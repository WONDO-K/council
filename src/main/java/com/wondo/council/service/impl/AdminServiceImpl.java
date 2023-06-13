package com.wondo.council.service.impl;

import com.wondo.council.domain.User;
import com.wondo.council.domain.enums.Role;
import com.wondo.council.dto.admin.ApprovalRequestDto;
import com.wondo.council.dto.exception.user.UserNotFoundException;
import com.wondo.council.repository.UserRepository;
import com.wondo.council.service.AdminService;
import com.wondo.council.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public void approvalSignUp(ApprovalRequestDto requestDto) throws AuthenticationException {
        User admin = userService.getMyInfo();
        if (admin.getRole().equals(Role.ADMIN)){
            User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new UserNotFoundException());
            user.approvalSignUp(requestDto.getIsMember());
            userRepository.save(user);
            log.info("가입 승인 처리가 완료 되었습니다.");
        } else {
            log.info("관리자 권한으로만 처리할 수 있습니다.");
            throw new AuthenticationException();
        }
    }

}
