package com.wondo.council.repository;

import com.wondo.council.domain.User;
import com.wondo.council.domain.Vote;
import com.wondo.council.domain.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {

    Optional<VoteRecord> findByUserAndVote(User user, Vote vote);
}
