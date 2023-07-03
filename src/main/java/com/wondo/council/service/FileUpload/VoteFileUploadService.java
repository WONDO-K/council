package com.wondo.council.service.FileUpload;

import org.springframework.web.multipart.MultipartFile;

public interface VoteFileUploadService {

    String uploadFile(MultipartFile file);


}
