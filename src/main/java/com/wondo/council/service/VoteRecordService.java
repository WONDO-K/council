package com.wondo.council.service;

import com.wondo.council.domain.User;
import com.wondo.council.domain.Vote;
import com.wondo.council.domain.enums.VoteOption;

public interface VoteRecordService {
    void createVoteRecord(User user, Vote vote, VoteOption voteOption);
}
