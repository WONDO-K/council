package com.wondo.council.dto.article;

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
@ApiModel(value = "ArticleRequestDto", description = "게시글 작성 요청 Dto")
public class ArticleRequestDto {

    @ApiModelProperty(name = "title")
    @NotBlank
    private String title;

    @ApiModelProperty(name = "content")
    @NotBlank
    private String content;

}
