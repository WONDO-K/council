package com.wondo.council.service;

import com.wondo.council.domain.enums.TradeCategory;
import com.wondo.council.dto.trade.TradeDto;
import com.wondo.council.dto.trade.TradeRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface TradeService {
    void createTrade(TradeRequestDto tradeRequestDto, MultipartFile[] imageFiles,TradeCategory tradeCategory);

    void likes(Long tradeUid);

    TradeDto getTrade(Long uid);

}