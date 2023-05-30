package com.wondo.council.controller;

import com.wondo.council.dto.exception.common.InvalidParameterException;
import com.wondo.council.dto.exception.user.DuplicateIdException;
import com.wondo.council.dto.user.SignUpRequestDto;
import com.wondo.council.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    @ApiOperation(value = "회원 가입", notes = "회원정보를 통해 회원 가입처리")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult result){
        if (result.hasErrors()){
            throw new InvalidParameterException(result);
        } else if (userService.checkId(requestDto.getId())){
            throw new DuplicateIdException();
        } else if (userService.checkEmail(requestDto.getEmail())){
            throw new DuplicateIdException();
        }
        userService.signup(requestDto);
        return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
    }


    @GetMapping("/auth/check/nickname/{nickname}")
    @ApiOperation(value = "닉네임 중복검사",notes = "해당 닉네임이 중복되는지 검사한다.")
    public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname){
        return new ResponseEntity<>(userService.checkNickname(nickname), HttpStatus.OK);
    }
    @GetMapping("/auth/check/id/{id}")
    @ApiOperation(value = "아이디 중복검사", notes = "해당 아이디가 중복되는지 검사한다.")
    public ResponseEntity<Boolean> checkId(@PathVariable String username) {
        return new ResponseEntity<>(userService.checkId(username), HttpStatus.OK);
    }

    @GetMapping("/auth/check/email/{email}")
    @ApiOperation(value = "이메일 중복검사", notes = "해당 이메일이 중복되는지 검사한다.")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.checkEmail(email), HttpStatus.OK);
    }


}
