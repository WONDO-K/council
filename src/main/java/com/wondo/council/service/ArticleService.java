package com.wondo.council.service;

import com.wondo.council.dto.article.ArticleDto;
import com.wondo.council.dto.article.ArticleRequestDto;

import java.util.List;

public interface ArticleService {
    void createArticle(ArticleRequestDto articleRequestDto);

    List<ArticleDto> getArticleList();

    ArticleDto getArticle(Long uid);
}
