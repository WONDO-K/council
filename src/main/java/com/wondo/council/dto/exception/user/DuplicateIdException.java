package com.wondo.council.dto.exception.user;


import com.wondo.council.dto.exception.CustomException;
import com.wondo.council.dto.exception.ErrorCode;

public class DuplicateIdException extends CustomException {

    public DuplicateIdException() {
        super(ErrorCode.DUPLICATE_ID);
    }
}
