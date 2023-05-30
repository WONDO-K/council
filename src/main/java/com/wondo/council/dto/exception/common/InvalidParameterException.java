package com.wondo.council.dto.exception.common;


import com.wondo.council.dto.exception.CustomException;
import com.wondo.council.dto.exception.ErrorCode;
import org.springframework.validation.Errors;

//BindingResult를 만들어 주는 역할
public class InvalidParameterException extends CustomException {

    private final Errors errors;

    public InvalidParameterException(Errors errors){
        super(ErrorCode.INVALID_PARAMETER);
        this.errors = errors;
    }
}
