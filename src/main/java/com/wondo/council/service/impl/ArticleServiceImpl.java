package com.wondo.council.service.impl;

import com.wondo.council.domain.Article;
import com.wondo.council.domain.User;
import com.wondo.council.dto.article.ArticleDto;
import com.wondo.council.dto.article.ArticleRequestDto;
import com.wondo.council.repository.ArticleRepository;
import com.wondo.council.service.ArticleService;
import com.wondo.council.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserService userService;

    @Override
    public void createArticle(ArticleRequestDto requestDto) {
        User user = userService.getMyInfo();
        String regDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).toString();
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .regDate(regDate)
                .view(0)
                .user(user)
                .build();
        articleRepository.save(article);
        log.info("게시글 작성 완료");
    }
    @Override
    public List<ArticleDto> getArticleList(){
        List<ArticleDto> list = articleRepository.findAll().stream().map(m->ArticleDto.from(m))
                .collect(Collectors.toList());
        return list;
    }
}
