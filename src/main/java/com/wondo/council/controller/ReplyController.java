package com.wondo.council.controller;

import com.wondo.council.domain.Reply;
import com.wondo.council.dto.exception.article.PostNotFoundException;
import com.wondo.council.dto.reply.ReplyRequestDto;
import com.wondo.council.dto.reply.ReplyUpdateRequestDto;
import com.wondo.council.repository.ReplyRepository;
import com.wondo.council.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final ReplyRepository replyRepository;

    @PostMapping("/create")
    @ApiOperation(value = "댓글 작성",notes = "댓글을 작성한다.")
    public ResponseEntity<?> createReply(
            @RequestParam(required = false) @ApiParam(value = "댓글 uid") Long uid, // 댓글 uid가 null이면 부모 댓글 값이 존재하면 자식 댓글로 인식함.
            @RequestBody @ApiParam(value = "댓글 작성 Dto",required = true) ReplyRequestDto replyRequestDto
            ){
        replyService.createReply(uid,replyRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{uid}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.")
    public ResponseEntity<?> deleteReply(@PathVariable @ApiParam(value = "댓글 번호 uid",required = true) Long uid){
        Reply reply = replyRepository.findById(uid).orElseThrow(()->new PostNotFoundException());
        replyService.deleteReply(reply);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{uid}")
    @ApiOperation(value = "특정 게시글의 댓글 리스트 조회", notes = "게시글에 속한 댓글 리스트를 조회한다.")
    public ResponseEntity<?> getReplyList(@PathVariable @ApiParam(value = "게시글 번호 uid") Long uid){
        return new ResponseEntity<>(replyService.getReplyList(uid),HttpStatus.OK);
    }

    @PutMapping("/update/{uid}")
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.")
    public ResponseEntity<?> updateReply(
            @PathVariable @ApiParam(value = "댓글 번호 uid",required = true) Long uid,
            @RequestBody @ApiParam(value = "댓글 수정 Dto",required = true) ReplyUpdateRequestDto replyUpdateRequestDto
    ){
        replyService.updateReply(uid,replyUpdateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
