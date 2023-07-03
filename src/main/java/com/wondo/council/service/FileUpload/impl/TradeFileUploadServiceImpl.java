package com.wondo.council.service.FileUpload.impl;

import com.wondo.council.domain.Trade;
import com.wondo.council.domain.image.TradeImage;
import com.wondo.council.repository.TradeImageRepository;
import com.wondo.council.service.FileUpload.TradeFileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TradeFileUploadServiceImpl implements TradeFileUploadService {

    private final TradeImageRepository tradeImageRepository;


    private final ResourceLoader resourceLoader;

    // 파일이 저장되는 경로
    private static final String UPLOAD_DIR = "static/uploads";

    @Override
    public String uploadFile(MultipartFile file, Trade trade) {
        try {
            if(file!=null && !file.isEmpty()){
                String savedName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                // 파일 저장 경로 설정
                // String uploadPath = ResourceUtils.getFile("classpath:" + UPLOAD_DIR).getAbsolutePath() + File.separator + fileName;
                // ResourceUtils.getFile() 메서드는 클래스패스 상의 파일을 가져오는 기능을 제공하는데, 이 메서드를 사용하면 JAR 파일로 패키징된 경우에는 예외가 발생하게 된다.
                // ResourceLoader는 클래스패스 상의 파일을 가져오는 것 외에도 다양한 리소스를 로드할 수 있는 유연한 기능을 제공한다.
                // ResourceLoader -> 클래스 패스 상의 파일에 접근하기 위해 더 안전하고 권장되는 방법
                String uploadPath = resourceLoader.getResource("classpath:" + UPLOAD_DIR).getFile().getAbsolutePath() +  File.separator + savedName;
                log.info("uploadPath : " + uploadPath);
                File dest = new File(uploadPath);

                // 파일 저장
                // file.transferTo(dest) 부분은 MultipartFile에서 업로드된 파일을 지정된 대상 파일 경로에 저장하는 역할
                // 업로드된 파일 데이터를 대상 파일로 복사하는 동작을 수행
                file.transferTo(dest); // -> 업로드된 파일이 dest로 지정된 경로에 저장.

                // 이미지 정보 저장
                TradeImage tradeImage = TradeImage.builder()
                        .fileName(file.getOriginalFilename())
                        .savedName(savedName)
                        .filePath(uploadPath)
                        .fileSize(file.getSize())
                        .delete_yn(false)
                        .trade(trade)
                        .build();

                tradeImageRepository.save(tradeImage);
                log.info("이미지 저장 완료");
                return uploadPath;
            } else {
                log.info("업로드할 이미지가 없습니다.");
                return null;
            }
        } catch (IOException e){
            // 파일 업로드 중에 예외가 발생한 경우 처리
            log.info("파일 업로드 중 예외 발생");
            e.printStackTrace();
        }
        return null;
    }
}
