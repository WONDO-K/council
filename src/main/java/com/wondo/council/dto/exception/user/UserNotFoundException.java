package com.wondo.council.dto.exception.user;


import com.wondo.council.dto.exception.CustomException;
import com.wondo.council.dto.exception.ErrorCode;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
