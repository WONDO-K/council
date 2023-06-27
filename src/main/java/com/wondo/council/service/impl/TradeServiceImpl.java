package com.wondo.council.service.impl;

import com.wondo.council.domain.Trade;
import com.wondo.council.domain.User;
import com.wondo.council.domain.enums.TradeCategory;
import com.wondo.council.domain.enums.TradeStatus;
import com.wondo.council.dto.trade.TradeRequestDto;
import com.wondo.council.repository.TradeRepository;
import com.wondo.council.service.FileUploadService;
import com.wondo.council.service.TradeService;
import com.wondo.council.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final FileUploadService fileUploadService;
    private final UserService userService;

    @Override
    public void createTrade(TradeRequestDto tradeRequestDto, MultipartFile imageFile, TradeCategory tradeCategory) {
        String uploadedFilePath;

        if (imageFile.isEmpty()){
            uploadedFilePath = null;
        } else {
            uploadedFilePath = fileUploadService.uploadFile(imageFile);
        }
        User user = userService.getMyInfo();
        String regDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).toString();

        Trade trade = Trade.builder()
                .title(tradeRequestDto.getTitle())
                .content(tradeRequestDto.getContent())
                .price(tradeRequestDto.getPrice())
                .imageUrl(uploadedFilePath)
                .regDate(regDate)
                .view(0)
                .tradeStatus(TradeStatus.ONGOING)
                .tradeCategory(tradeCategory)
                .user(user)
                .build();
        tradeRepository.save(trade);
        log.info("거래 게시물 생성 완료.");
    }
}
