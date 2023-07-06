package com.wondo.council.controller;

import com.wondo.council.dto.trade.TradeRequestDto;
import com.wondo.council.service.TradeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping(value = "/create",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "거래 게시글 생성", notes = "거래 게시글을 생성한다.")
    public ResponseEntity<?> createTrade(
            @ModelAttribute @ApiParam(value = "거래 게시글 생성 Dto") TradeRequestDto tradeRequestDto,
            @RequestPart @ApiParam(value = "거래 이미지", required = false) MultipartFile[] imageFiles
    ) {
        tradeService.createTrade(tradeRequestDto, imageFiles);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/like")
    @ApiOperation(value = "거래 게시글 찜",notes = "거래 게시글을 찜한다.")
    public ResponseEntity<?> likes(
            @RequestParam @ApiParam(value = "찜할 거래 게시글 번호 uid",required = true) Long tradeUid
    ){
        tradeService.likes(tradeUid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{uid}")
    @ApiOperation(value = "거래 게시글 조회", notes = "특정 거래 게시글을 조회한다.")
    public ResponseEntity<?> getTrade(
            @PathVariable @ApiParam(value = "조회할 특정 거래 게시글 번호 uid",required = true) Long uid
    ){
        return new ResponseEntity<>(tradeService.getTrade(uid),HttpStatus.OK);
    }
}