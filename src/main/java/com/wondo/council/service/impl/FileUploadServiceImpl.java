package com.wondo.council.service.impl;

import com.wondo.council.domain.Image;
import com.wondo.council.repository.ImageRepository;
import com.wondo.council.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class FileUploadServiceImpl implements FileUploadService {

    private final ImageRepository imageRepository;

    private final ResourceLoader resourceLoader;

    private static final String UPLOAD_DIR = "static/uploads";

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            if(!file.isEmpty()){
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                // 파일 저장 경로 설정
                //String uploadPath = ResourceUtils.getFile("classpath:" + UPLOAD_DIR).getAbsolutePath() + File.separator + fileName;
                String uploadPath = resourceLoader.getResource("classpath:" + UPLOAD_DIR).getFile().getAbsolutePath() +  File.separator + fileName;
                log.info("uploadPath : " + uploadPath);
                File dest = new File(uploadPath);

                // 파일 저장
                file.transferTo(dest);

                // 이미지 정보 저장
                Image image = new Image();
                image.setFileName(fileName);
                image.setFilePath(uploadPath);
                image.setFileSize(file.getSize());

                imageRepository.save(image);
                log.info("이미지 저장 완료");
                return uploadPath;

            }
        } catch (IOException e){
            // 파일 업로드 중에 예외가 발생한 경우 처리
            log.info("파일 업로드 중 예외 발생");
            e.printStackTrace();
        }
        return null;
    }
}
