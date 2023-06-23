package com.wondo.council.service;

import com.wondo.council.dto.article.ArticleRequestDto;
import com.wondo.council.dto.inquiry.InquiryDto;

public interface InquiryService {
    void createInquiry(ArticleRequestDto inquiryRequestDto);

    InquiryDto getInquiry(Long uid);

    void updateInquiry(Long uid, ArticleRequestDto inquiryRequestDto);
}
