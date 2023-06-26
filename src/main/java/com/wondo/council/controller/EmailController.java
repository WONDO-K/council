package com.wondo.council.controller;

import com.wondo.council.service.EmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/any/emailConfirm")
    @ApiOperation(value = "이메일 인증번호 전송", notes = "이메일에 인증번호를 전송한다.")
    public String emailConfirm(@RequestParam String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        return confirm;
    }

    @GetMapping("any/verifyEmail")
    @ApiOperation(value = "이메일 인증번호 검증", notes = "이메일에 인증번호를 검증한다.")
    public Boolean verifyEmail(
            @RequestParam @ApiParam(value = "메일로 전송받은 인증번호",required = true) String key
    ) throws ChangeSetPersister.NotFoundException {
        Boolean result = emailService.verifyEmail(key);
        return result;
    }
}
