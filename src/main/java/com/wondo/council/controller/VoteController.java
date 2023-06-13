package com.wondo.council.controller;

import com.wondo.council.dto.vote.VoteRequestDto;
import com.wondo.council.service.VoteService;
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
    public ResponseEntity<?> createVote(
            @ModelAttribute @ApiParam(value = "투표 안건 생성 Dto",required = true) VoteRequestDto voteReqeustDto,
            @RequestPart @ApiParam(value = "이미지") MultipartFile imageFile
            ){
        voteService.createVote(voteReqeustDto,imageFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
