package com.wondo.council.service;

import com.wondo.council.dto.trade.TradeRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface TradeService {
    void createTrade(TradeRequestDto tradeRequestDto, MultipartFile[] imageFilesimageFiles);
}