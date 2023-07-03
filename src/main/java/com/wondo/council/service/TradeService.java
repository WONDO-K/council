package com.wondo.council.service;

import com.wondo.council.domain.enums.TradeCategory;
import com.wondo.council.dto.trade.TradeRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TradeService {
    void createTrade(TradeRequestDto tradeRequestDto, MultipartFile[] imageFilesimageFiles);
}