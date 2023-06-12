package com.wondo.council.controller;

import com.wondo.council.dto.notice.NoticeRequestDto;
import com.wondo.council.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/create")
    @ApiOperation(value = "공지사항 작성",notes = "공지사항을 작성한다.")
    public ResponseEntity<?> createNotice(@RequestBody @ApiParam(value = "공지사항 생성 Dto",required = true) NoticeRequestDto noticeRequestDto){
        noticeService.createNotice(noticeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
