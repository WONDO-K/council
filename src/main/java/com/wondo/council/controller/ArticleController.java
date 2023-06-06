package com.wondo.council.controller;

import com.wondo.council.dto.article.ArticleDto;
import com.wondo.council.dto.article.ArticleRequestDto;
import com.wondo.council.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/create")
    @ApiOperation(value = "게시글 작성",notes = "게시글을 작성한다.")
    public ResponseEntity<?> createArticle(@RequestBody @ApiParam(value = "게시글 생성 Dto",required = true) ArticleRequestDto articleRequestDto){
        articleService.createArticle(articleRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/any/articleList")
    @ApiOperation(value = "게시글 리스트 조회",notes = "게시글 리스트를 조회한다.")
    public ResponseEntity<?> getArticleList(){
        return new ResponseEntity<>(articleService.getArticleList(), HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    @ApiOperation(value = "게시글 조회", notes = "게시글을 조회한다.")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable Long uid) {
        return new ResponseEntity<>(articleService.getArticle(uid), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{uid}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    public ResponseEntity<?> deleteArticle(
            @PathVariable @ApiParam(value = "게시글 번호 uid",required = true) Long uid
    ){
        articleService.deleteArticle(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
