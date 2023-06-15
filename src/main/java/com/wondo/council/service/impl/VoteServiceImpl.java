package com.wondo.council.service.impl;

import com.wondo.council.domain.User;
import com.wondo.council.domain.Vote;
import com.wondo.council.domain.VoteRecord;
import com.wondo.council.domain.enums.Role;
import com.wondo.council.domain.enums.VoteOption;
import com.wondo.council.dto.exception.article.PostNotFoundException;
import com.wondo.council.dto.vote.VoteDto;
import com.wondo.council.dto.vote.VoteRequestDto;
import com.wondo.council.repository.VoteRecordRepository;
import com.wondo.council.repository.VoteRepository;
import com.wondo.council.service.FileUploadService;
import com.wondo.council.service.UserService;
import com.wondo.council.service.VoteRecordService;
import com.wondo.council.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final FileUploadService fileUploadService;
    private final UserService userService;

    private final VoteRecordService voteRecordService;

    private final VoteRecordRepository voteRecordRepository;
    @Override
    public void createVote(VoteRequestDto voteRequestDto, MultipartFile imageFile) {

        String uploadedFilePath;

        if (imageFile.isEmpty()){
            uploadedFilePath = null;
        } else {
            uploadedFilePath = fileUploadService.uploadFile(imageFile);
        }
        User admin = userService.getMyInfo();

        if (admin.getRole().equals(Role.ADMIN)){
            Vote vote = Vote.builder()
                    .title(voteRequestDto.getTitle())
                    .content(voteRequestDto.getContent())
                    .imageUrl(uploadedFilePath)
                    .closed(false)
                    .yesCount(0)
                    .noCount(0)
                    .build();
            voteRepository.save(vote);
            log.info("투표 안건 생성 완료 : {}", voteRequestDto.getTitle());
        } else {
            log.info("관리자 권한으로만 투표 안건을 생성할 수 있습니다.");
            throw new AccessDeniedException("관리자 권한으로만 투표 안건을 생성할 수 있습니다.");
        }


    }

    @Override
    public void voting(Long uid,VoteOption voteOption) {
        User user = userService.getMyInfo();
        Vote vote = voteRepository.findById(uid).orElseThrow(()-> new PostNotFoundException());
        Optional<VoteRecord> voteRecord = voteRecordRepository.findByUserAndVote(user,vote);

        // 투표가 진행 중 이라면
        if (vote.isClosed() == false){
            if (voteRecord.isPresent()){ // 투표에 참여한 기록이 있다면 투표 불가
                log.info("이미 투표에 참여하였습니다.");
                throw new AccessDeniedException("이미 투표에 참여하였습니다.");
            } else { // 투표에 참여한 기록이 없다면 투표 가능
                if (voteOption.equals(VoteOption.YES)){
                    vote.addYesCount();
                } else {
                    vote.addNoCount();
                }
                // 투표 안건의 찬/반의 수를 증가시키고 VoteRecord를 저장한다.
                voteRecordService.createVoteRecord(user, vote, voteOption);
            }
            // 투표가 종료되었다면
        } else {
            log.info("투표가 종료된 안건입니다.");
            throw new AccessDeniedException("투표가 종료된 안건입니다.");
        }

    }

    @Override
    public VoteDto getVote(Long uid) {
        Optional<Vote> vote = voteRepository.findById(uid);

        if (vote.get().isClosed() == false){
            log.info("투표 진행 중인 안건 조회 완료.");
            return VoteDto.from(vote.get());
        } else {
            log.info("종료된 투표 안건 조회 완료.");
            return VoteDto.closedFrom(vote.get());
        }
    }
}
