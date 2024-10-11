package com.example.telegrambotbobrai.service;

import com.example.telegrambotbobrai.db.MessageLogEntity;
import com.example.telegrambotbobrai.db.MessageLogEntityRepository;
import com.example.telegrambotbobrai.dto.UserMessageLogDTO;
import com.example.telegrambotbobrai.utils.ApiListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogsService {
    private final MessageLogEntityRepository messageLogEntityRepository;

    public ApiListResponse<UserMessageLogDTO> getMessagesByUserId(Long userId, Integer page, Integer perPage, String dateFrom, String dateTo) {
        LocalDateTime dateTimeFrom = LocalDateTime.parse(dateFrom == null? "2024-01-01 00:00" : dateFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime dateTimeTo = LocalDateTime.parse(dateTo == null? "3024-01-01 00:00" : dateTo , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Page<MessageLogEntity> messageLogEntities = messageLogEntityRepository.findByUser_IdAndTimeGreaterThanEqualAndTimeLessThanEqual(userId, dateTimeFrom, dateTimeTo, PageRequest.of(page - 1, perPage));
        ApiListResponse<UserMessageLogDTO> response = new ApiListResponse<>();
        response.setResult(messageLogEntities.map(it -> new UserMessageLogDTO(it, it.getUser())).toList());
        response.setTotal(messageLogEntities.getTotalPages());
        return response;
    }

    public ApiListResponse<UserMessageLogDTO> getMessages(Integer page, Integer perPage, String dateFrom, String dateTo) {
        LocalDateTime dateTimeFrom = LocalDateTime.parse(dateFrom == null? "2024-01-01 00:00" : dateFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime dateTimeTo = LocalDateTime.parse(dateTo == null? "3024-01-01 00:00" : dateTo , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Page<MessageLogEntity> messageLogEntities = messageLogEntityRepository.findByTimeGreaterThanEqualAndTimeLessThanEqual(dateTimeFrom, dateTimeTo, PageRequest.of(page - 1, perPage));
        ApiListResponse<UserMessageLogDTO> response = new ApiListResponse<>();
        response.setResult(messageLogEntities.map(it -> new UserMessageLogDTO(it, it.getUser())).toList());
        response.setTotal(messageLogEntities.getTotalPages());
        return response;
    }
}
