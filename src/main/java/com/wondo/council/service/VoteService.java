package com.wondo.council.service;

import com.wondo.council.dto.vote.VoteRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface VoteService {
    void createVote(VoteRequestDto voteReqeustDto, MultipartFile imageFile);
}
