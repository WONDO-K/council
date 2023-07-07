package com.wondo.council.service.impl;

import com.wondo.council.domain.Trade;
import com.wondo.council.domain.TradeLike;
import com.wondo.council.domain.User;
import com.wondo.council.domain.enums.TradeCategory;
import com.wondo.council.domain.enums.TradeStatus;
import com.wondo.council.dto.exception.article.PostNotFoundException;
import com.wondo.council.dto.trade.TradeDto;
import com.wondo.council.dto.trade.TradeRequestDto;
import com.wondo.council.repository.TradeLikeRepository;
import com.wondo.council.repository.TradeRepository;
import com.wondo.council.service.FileUpload.TradeFileUploadService;
import com.wondo.council.service.TradeService;
import com.wondo.council.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final TradeLikeRepository tradeLikeRepository;
    private final TradeFileUploadService tradeFileUploadService;
    private final UserService userService;

    @Override
    public void createTrade(TradeRequestDto tradeRequestDto, MultipartFile[] imageFiles, TradeCategory tradeCategory) {

        User user = userService.getMyInfo();
        String regDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).toString();

        Trade trade = Trade.builder()
                .title(tradeRequestDto.getTitle())
                .content(tradeRequestDto.getContent())
                .price(tradeRequestDto.getPrice())
                .regDate(regDate)
                .view(0)
                .tradeStatus(TradeStatus.ONGOING)
                .tradeCategory(tradeCategory)
                .user(user)
                .build();
        tradeRepository.save(trade);
        log.info("파일 업로드 전 거래 Trade 객체 생성 완료.");

        List<String> uploadedFilePaths = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles){
            if (imageFile.isEmpty()){
                uploadedFilePaths.add(null);
            } else {
                uploadedFilePaths.add(tradeFileUploadService.uploadFile(imageFile,trade));
            }

        }

        trade.setImageUrls(uploadedFilePaths);
        tradeRepository.save(trade);

        log.info("거래 게시물 생성 완료.");
    }

    @Override
    public void likes(Long tradeUid) {
        User user = userService.getMyInfo();
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).toString();
        Optional<Trade> trade = tradeRepository.findById(tradeUid);

        if(!trade.isPresent()){
            TradeLike tradeLike = TradeLike.builder()
                    .user(user)
                    .trade(trade.get())
                    .date(date)
                    .build();
            tradeLikeRepository.save(tradeLike);
        }else {
            log.info("게시글이 존재 하지 않습니다.");
            throw new PostNotFoundException();
        }

    }

    @Override
    public TradeDto getTrade(Long uid) {
        Optional<Trade> trade = tradeRepository.findById(uid);
        if (trade.isPresent()){
            log.info("거래 게시글 조회 완료.");
            trade.get().addViewCount();
            tradeRepository.save(trade.get());
            return TradeDto.from(trade.get());
        } else {
            log.info("거래 게시글이 존재하지 않습니다.");
            throw new PostNotFoundException();
        }
    }


}