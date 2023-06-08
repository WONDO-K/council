package com.wondo.council.dto.reply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ReplyRequestDto", description = "댓글 요청 Dto")
public class ReplyRequestDto {

    @NotNull
    private Long articleUid;

    @ApiModelProperty(name = "content")
    private String content;

}
