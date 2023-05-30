package com.wondo.council.dto.exception.user;


import com.wondo.council.dto.exception.CustomException;
import com.wondo.council.dto.exception.ErrorCode;

public class DuplicateEmailException extends CustomException {
    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
