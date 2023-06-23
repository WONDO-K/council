package com.wondo.council.service;

import com.wondo.council.domain.Inquiry;
import com.wondo.council.dto.article.ArticleRequestDto;
import com.wondo.council.dto.inquiry.InquiryDto;

import java.util.List;

public interface InquiryService {
    void createInquiry(ArticleRequestDto inquiryRequestDto);

    InquiryDto getInquiry(Long uid);

    void updateInquiry(Long uid, ArticleRequestDto inquiryRequestDto);

    List<InquiryDto> getInquiryList();
}
