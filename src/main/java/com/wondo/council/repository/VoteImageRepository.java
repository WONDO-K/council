package com.wondo.council.repository;

import com.wondo.council.domain.image.VoteImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteImageRepository extends JpaRepository<VoteImage, Long> {
}
