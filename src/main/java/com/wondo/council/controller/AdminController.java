package com.wondo.council.controller;

import com.wondo.council.dto.admin.ApprovalRequestDto;
import com.wondo.council.service.AdminService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/approval")
    @ApiOperation(value = "가입 승인", notes = "관리자가 가입자의 가입 승인을 처리한다.")
    public ResponseEntity<String> ApprovalSignUp(@RequestBody ApprovalRequestDto requestDto) throws AuthenticationException {
        adminService.approvalSignUp(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
