package com.wondo.council.service;

import com.wondo.council.dto.admin.ApprovalRequestDto;

import javax.security.sasl.AuthenticationException;

public interface AdminService {
    void approvalSignUp(ApprovalRequestDto requestDto) throws AuthenticationException;
}
