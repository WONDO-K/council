package com.wondo.council.dto.vote;

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
@ApiModel(value = "VoteRequestDto", description = "투표 안건 생성 요청 Dto")
public class VoteRequestDto {

    @ApiModelProperty(name = "title")
    @NotBlank
    private String title;

    @ApiModelProperty(name = "content")
    @NotBlank
    private String content;

}
