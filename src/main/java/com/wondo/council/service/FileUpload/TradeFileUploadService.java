package com.wondo.council.service.FileUpload;

import com.wondo.council.domain.Trade;
import org.springframework.web.multipart.MultipartFile;

public interface TradeFileUploadService {
    String uploadFile(MultipartFile file, Trade trade);
}
