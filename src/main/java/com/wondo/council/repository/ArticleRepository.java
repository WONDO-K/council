package com.wondo.council.repository;

import com.wondo.council.domain.Article;
import com.wondo.council.dto.article.ArticleDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByViewDesc();

    List<Article> findAllByUserNickname(String nickname);
}
