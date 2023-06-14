package com.wondo.council.repository;

import com.wondo.council.domain.Notice;
import com.wondo.council.domain.User;
import com.wondo.council.domain.Vote;
import com.wondo.council.domain.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {

}
