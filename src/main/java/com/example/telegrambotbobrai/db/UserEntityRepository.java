package com.example.telegrambotbobrai.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByChatId(Long chatId);

    Optional<UserEntity> findByChatId(Long chatId);
}