package com.wondo.council.service.impl;

import com.wondo.council.domain.User;
import com.wondo.council.domain.Vote;
import com.wondo.council.domain.enums.Role;
import com.wondo.council.dto.vote.VoteRequestDto;
import com.wondo.council.repository.VoteRepository;
import com.wondo.council.service.FileUploadService;
import com.wondo.council.service.UserService;
import com.wondo.council.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final FileUploadService fileUploadService;
    private final UserService userService;
    @Override
    public void createVote(VoteRequestDto voteRequestDto, MultipartFile imageFile) {

        String uploadedFilePath = fileUploadService.uploadFile(imageFile);
        User admin = userService.getMyInfo();

        if (admin.getRole().equals(Role.ADMIN)){
            Vote vote = Vote.builder()
                    .title(voteRequestDto.getTitle())
                    .content(voteRequestDto.getContent())
                    .imageUrl(uploadedFilePath)
                    .build();
            voteRepository.save(vote);
            log.info("투표 안건 생성 완료 : {}", voteRequestDto.getTitle());
        } else {
            log.info("관리자 권한으로만 투표 안건을 생성할 수 있습니다.");
            throw new AccessDeniedException("관리자 권한으로만 투표 안건을 생성할 수 있습니다.");
        }


    }
}
