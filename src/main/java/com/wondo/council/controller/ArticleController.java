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

    @GetMapping("/any/articleList/sort")
    @ApiOperation(value = "조회수 순으로 정렬된 리스트 조회", notes = "조회수 순으로 정렬된 리스트를 조회한다.")
    public ResponseEntity<?> getSortAticleList(){
        return new ResponseEntity<>(articleService.getSortArticleList(),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{uid}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    public ResponseEntity<?> deleteArticle(
            @PathVariable @ApiParam(value = "게시글 번호 uid",required = true) Long uid
    ){
        articleService.deleteArticle(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{uid}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    public ResponseEntity<?> updateArticle(
            @PathVariable @ApiParam(value = "게시글 번호 uid",required = true) Long uid,
            @RequestBody @ApiParam(value = "게시글 수정 Dto",required = true) ArticleRequestDto articleRequestDto
    ){
        articleService.updateArticle(uid,articleRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/any/articleList/{nickname}")
    @ApiOperation(value = "작성자 검색 게시글 리스트",notes = "검색한 작성자가 작성한 게시글을 조회한다.")
    public ResponseEntity<?> getArticleListByUsername(
            @PathVariable @ApiParam(value = "검색할 유저 닉네임",required = true) String nickname
    ){
        return new ResponseEntity<>(articleService.getArticleListByUsername(nickname),HttpStatus.OK);
    }
}
