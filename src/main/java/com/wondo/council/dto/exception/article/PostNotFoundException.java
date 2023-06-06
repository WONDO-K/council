package com.wondo.council.dto.exception.article;

import com.wondo.council.dto.exception.CustomException;
import com.wondo.council.dto.exception.ErrorCode;

public class PostNotFoundException extends CustomException {

    public PostNotFoundException() {super(ErrorCode.POST_NOT_FOUND);}
}
