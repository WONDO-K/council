package com.wondo.council.controller;

import com.wondo.council.domain.enums.VoteOption;
import com.wondo.council.dto.vote.VoteRequestDto;
import com.wondo.council.service.VoteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;


    @PostMapping("/create")
    @ApiOperation(value = "투표 안건 생성", notes = "투표 안건을 생성한다.")
    public ResponseEntity<?> createVote(
            @ModelAttribute @ApiParam(value = "투표 안건 생성 Dto",required = true) VoteRequestDto voteReqeustDto,
            @RequestPart @ApiParam(value = "이미지", required = false) MultipartFile imageFile
            ){
        voteService.createVote(voteReqeustDto,imageFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/voting")
    @ApiOperation(value = "투표권 행사", notes = "투표권을 행사한다.")
    public ResponseEntity<?> voting(
            @RequestParam @ApiParam(value = "투표 안건 번호 uid",required = true) Long uid,
            @RequestParam @ApiParam(value = "투표권 행사", required = true) VoteOption voteOption
            ){
        voteService.voting(uid,voteOption);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    @ApiOperation(value = "투표 안건 조회", notes = "투표 안건을 조회한다.")
    public ResponseEntity<?> getVote(
            @PathVariable @ApiParam(value = "투표 안건 번호 uid",required = true) Long uid
    ){
        return new ResponseEntity<>(voteService.getVote(uid),HttpStatus.OK);
    }
}
