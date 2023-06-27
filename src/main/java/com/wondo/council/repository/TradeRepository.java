package com.wondo.council.repository;

import com.wondo.council.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
