package com.wondo.council.dto.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "NoticeRequestDto", description = "공지사항 작성 요청 Dto")
public class NoticeRequestDto {

    @ApiModelProperty(name = "title")
    @NotBlank
    private String title;

    @ApiModelProperty(name = "content")
    @NotBlank
    private String content;
}
