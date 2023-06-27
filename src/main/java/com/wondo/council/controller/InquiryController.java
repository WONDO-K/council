package com.wondo.council.controller;

import com.wondo.council.dto.article.ArticleRequestDto;
import com.wondo.council.dto.inquiry.InquiryDto;
import com.wondo.council.service.InquiryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@RequestMapping("/api/v1/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping("/create")
    @ApiOperation(value = "문의글 작성", notes = "문의글을 작성한다.")
    public ResponseEntity<?> createInquiry(
            @RequestBody @ApiParam(value = "문의글 작성 Dto",required = true)ArticleRequestDto inquiryRequestDto
            ){
        inquiryService.createInquiry(inquiryRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{uid}")
    @ApiOperation(value = "문의글 조회", notes = "문의글을 조회한다.")
    public ResponseEntity<InquiryDto> getInquiry(
            @PathVariable @ApiParam(value = "문의글 번호 uid",required = true) Long uid
    ){
        return new ResponseEntity<>(inquiryService.getInquiry(uid),HttpStatus.OK);
    }

    @PutMapping("update/{uid}")
    @ApiOperation(value = "문의글 수정",notes = "문의글을 수정한다.")
    public ResponseEntity<?> updateInquiry(
            @PathVariable @ApiParam(value = "문의글 번호 uid",required = true) Long uid,
            @RequestBody @ApiParam(value = "문의글 수정 Dto",required = true) ArticleRequestDto inquiryRequestDto
    ){
        inquiryService.updateInquiry(uid,inquiryRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/any/inquiryList")
    @ApiOperation(value = "문의글 리스트 조회", notes = "문의글 리스트를 조회한다.")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getInquiryList(){
        return new ResponseEntity<>(inquiryService.getInquiryList(),HttpStatus.OK);
    }
}
