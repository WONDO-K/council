package com.wondo.council.service;

import com.wondo.council.domain.enums.VoteOption;
import com.wondo.council.dto.vote.VoteRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface VoteService {
    void createVote(VoteRequestDto voteReqeustDto, MultipartFile imageFile);

    void voting(Long uid,VoteOption voteOption);

    Object getVote(Long uid);

    void closingVote(Long uid, boolean isClosed);
}
