package com.wondo.council.dto.article;

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
@ApiModel(value = "ArticleRequestDto", description = "게시글 작성 요청 Dto")
public class ArticleRequestDto {
    @ApiModelProperty(name = "title")
    private String title;
    @ApiModelProperty(name = "content")
    private String content;

}
