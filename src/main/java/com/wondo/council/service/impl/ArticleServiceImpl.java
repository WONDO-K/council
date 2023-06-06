package com.wondo.council.service.impl;

import com.wondo.council.domain.Article;
import com.wondo.council.domain.User;
import com.wondo.council.dto.article.ArticleDto;
import com.wondo.council.dto.article.ArticleRequestDto;
import com.wondo.council.dto.exception.article.PostNotFoundException;
import com.wondo.council.repository.ArticleRepository;
import com.wondo.council.service.ArticleService;
import com.wondo.council.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
        log.info("게시글 작성 완료.");
    }
    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getArticleList(){
        List<ArticleDto> list = articleRepository.findAll().stream().map(m->ArticleDto.from(m))
                .collect(Collectors.toList());
        log.info("게시글 리스트 조회 완료.");
        return list;
    }

    @Override
    // readOnly를 적용했더니 addViewCount()가 작동하지 않음 -> readOnly는 데이터의 수정이나 저장하는 작업을 수행할 수 없다.
    public ArticleDto getArticle(Long uid) {
        Article article = articleRepository.findById(uid).orElseThrow(PostNotFoundException::new);
        article.addViewCount();
        articleRepository.save(article);
        log.info("특정 게시글 조회 완료.");
        return ArticleDto.from(article);
    }

    @Override
    public void deleteArticle(Long uid) {
        articleRepository.deleteById(uid);
        log.info("게시글 삭제 완료.");
    }

    @Override
    public void updateArticle(Long uid, ArticleRequestDto articleRequestDto) {
        Optional<Article> article = articleRepository.findById(uid);
        if (article.isPresent()){
            String upDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).toString();
            if (article.get().getUser() == userService.getMyInfo()){
                Article newArticle = Article.builder()
                        .uid(uid)
                        .title(articleRequestDto.getTitle())
                        .content(articleRequestDto.getContent())
                        .view(article.get().getView())
                        .user(userService.getMyInfo())
                        .upDate(upDate)
                        .build();
                articleRepository.save(newArticle);
                log.info("게시글 수정이 완료되었습니다.");
            }
        }
    }
}
