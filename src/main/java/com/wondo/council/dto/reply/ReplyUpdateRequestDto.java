package com.wondo.council.dto.reply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ReplyUpdateRequestDto", description = "댓글 수정 요청 Dto")
public class ReplyUpdateRequestDto {

    @ApiModelProperty(name = "content")
    private String content;

}
