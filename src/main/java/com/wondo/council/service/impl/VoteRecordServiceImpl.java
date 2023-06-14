package com.wondo.council.service.impl;

import com.wondo.council.domain.User;
import com.wondo.council.domain.Vote;
import com.wondo.council.domain.VoteRecord;
import com.wondo.council.domain.enums.VoteOption;
import com.wondo.council.repository.VoteRecordRepository;
import com.wondo.council.service.VoteRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class VoteRecordServiceImpl implements VoteRecordService {

    private final VoteRecordRepository voteRecordRepository;

    @Override
    public void createVoteRecord(User user, Vote vote, VoteOption voteOption) {
        VoteRecord voteRecord = VoteRecord.builder()
                .user(user)
                .vote(vote)
                .voteOption(voteOption)
                .build();
        voteRecordRepository.save(voteRecord);
        log.info("투표 기록 저장 완료.");
    }
}
