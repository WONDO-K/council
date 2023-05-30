package com.wondo.council.dto.exception.user;

import com.wondo.council.dto.exception.CustomException;
import com.wondo.council.dto.exception.ErrorCode;

public class DuplicateNicknameException extends CustomException {
    public DuplicateNicknameException(){super(ErrorCode.DUPLICATE_NICKNAME);}
}
