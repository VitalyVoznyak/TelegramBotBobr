package com.example.telegrambotbobrai.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MessageLogEntityRepository extends JpaRepository<MessageLogEntity, Long> {
    Page<MessageLogEntity> findByTimeGreaterThanEqualAndTimeLessThanEqual(LocalDateTime timeFrom, LocalDateTime timeTo, Pageable pageable);

    Page<MessageLogEntity> findByUser_IdAndTimeGreaterThanEqualAndTimeLessThanEqual(Long id, LocalDateTime timeFrom, LocalDateTime timeTo, Pageable pageable);
}