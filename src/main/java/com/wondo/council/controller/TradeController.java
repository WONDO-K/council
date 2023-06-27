package com.wondo.council.controller;

import com.wondo.council.domain.enums.TradeCategory;
import com.wondo.council.dto.trade.TradeRequestDto;
import com.wondo.council.service.TradeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/create")
    @ApiOperation(value = "거래 게시글 생성", notes = "거래 게시글을 생성한다.")
    public ResponseEntity<?> createTrade(
            @ModelAttribute @ApiParam(value = "거래 게시글 생성 Dto") TradeRequestDto tradeRequestDto,
            @RequestPart @ApiParam(value = "이미지", required = false) MultipartFile imageFile,
            @RequestParam @ApiParam(value = "카테고리",required = true)TradeCategory tradeCategory
            ) {
        tradeService.createTrade(tradeRequestDto, imageFile,tradeCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
