package com.wondo.council.dto.article;

import com.wondo.council.domain.Article;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(value = "ArticleResponseDto",description = "게시글 정보 Dto")
public class ArticleDto {
    private Long uid;
    private String title;
    private String date;
    private int view;
    private String nickname;

    public static ArticleDto from(Article article){
        String theDate;
        if(article.getUpDate() == null){
            theDate = article.getRegDate();
        } else {
            theDate = article.getUpDate();
        }
        return ArticleDto.builder()
                .uid(article.getUid())
                .title(article.getTitle())
                .date(theDate)
                .view(article.getView())
                .nickname(article.getUser().getNickname())
                .build();
    }
}
