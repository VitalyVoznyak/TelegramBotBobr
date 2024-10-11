package com.example.telegrambotbobrai.controller;


import com.example.telegrambotbobrai.dto.UserMessageLogDTO;
import com.example.telegrambotbobrai.service.LogsService;
import com.example.telegrambotbobrai.utils.ApiListResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Validated
@RequestMapping("/logs")
public class LogsController {
    private final LogsService logsService;


    @Operation(
            summary = "Время передвавать в формате yyyy-MM-dd HH:mm"
    )
    @GetMapping()
    private ApiListResponse<UserMessageLogDTO> getMessages
            (@Valid
             @NotNull
             @Min(value = 1)
             @RequestParam Integer page,
             @Valid
             @NotNull
             @Min(value = 1)
             @RequestParam Integer perPage,
             @RequestParam(required = false) String dateTimeFrom,
             @RequestParam(required = false) String dateTimeTo) {
        return logsService.getMessages(page, perPage, dateTimeFrom, dateTimeTo);
    }

    @Operation(
            summary = "Время передвавать в формате yyyy-MM-dd HH:mm"
    )
    @GetMapping("/{userId}")
    private ApiListResponse<UserMessageLogDTO> getMessagesByUser
            (@PathVariable Long userId,
             @Valid
             @NotNull
             @Min(value = 1)
             @RequestParam Integer page,
             @Valid
             @NotNull
             @Min(value = 1)
             @RequestParam Integer perPage,
             @RequestParam(required = false) String dateTimeFrom,
             @RequestParam(required = false) String dateTimeTo) {
        return logsService.getMessagesByUserId(userId, page, perPage, dateTimeFrom, dateTimeTo);
    }
}
