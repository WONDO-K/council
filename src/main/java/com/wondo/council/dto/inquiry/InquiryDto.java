package com.wondo.council.dto.inquiry;

import com.wondo.council.domain.Inquiry;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(value = "InquiryResponseDto",description = "문의글 정보 Dto")
public class InquiryDto {
    private Long uid;
    private String title;
    private String content;
    private String date;
    private int view;
    private String nickname;

    public static InquiryDto from(Inquiry inquiry){
        String theDate;
        if(inquiry.getUpDate() == null){
            theDate = inquiry.getRegDate();
        } else {
            theDate = inquiry.getUpDate();
        }
        return InquiryDto.builder()
                .uid(inquiry.getUid())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .date(theDate)
                .view(inquiry.getView())
                .nickname(inquiry.getUser().getNickname())
                .build();
    }
}
