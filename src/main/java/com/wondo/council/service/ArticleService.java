package com.wondo.council.service;

import com.wondo.council.domain.Article;
import com.wondo.council.dto.article.ArticleDto;
import com.wondo.council.dto.article.ArticleRequestDto;

import java.util.List;

public interface ArticleService {
    void createArticle(ArticleRequestDto articleRequestDto);

    List<ArticleDto> getArticleList();

    ArticleDto getArticle(Long uid);

    void deleteArticle(Long uid);

    void updateArticle(Long uid, ArticleRequestDto articleRequestDto);

    List<ArticleDto> getSortArticleList();

    List<ArticleDto> getArticleListByUsername(String nickname);

    List<ArticleDto> getMyArticleList();
}
