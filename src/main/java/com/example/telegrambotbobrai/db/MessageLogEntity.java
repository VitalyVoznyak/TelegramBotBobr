package com.example.telegrambotbobrai.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "logs")
@NoArgsConstructor
public class MessageLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "bot_response")
    private String botResponse;

    @Column(name = "user_message_text")
    private String userMessageText;

    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    private UserEntity user;

    public MessageLogEntity(UserEntity userId, LocalDateTime time, String botResponse, String userMessageText) {
        this.user = userId;
        this.time = time;
        this.botResponse = botResponse;
        this.userMessageText = userMessageText;
    }
}
