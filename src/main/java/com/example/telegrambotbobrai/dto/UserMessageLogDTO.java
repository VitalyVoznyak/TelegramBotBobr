package com.example.telegrambotbobrai.dto;

import com.example.telegrambotbobrai.db.MessageLogEntity;
import com.example.telegrambotbobrai.db.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserMessageLogDTO {

    private Long id;

    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("user_id")
    private Long userId;

    private LocalDateTime time;

    @JsonProperty("bot_response")
    private String botResponse;

    @JsonProperty("user_message_text")
    private String userMessageText;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public UserMessageLogDTO(MessageLogEntity message, UserEntity user){
        this.id = message.getId();
        this.chatId = message.getUser().getChatId();
        this.time = message.getTime();
        this.botResponse = message.getBotResponse();
        this.userMessageText = message.getUserMessageText();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userId = user.getId();
    }
}
