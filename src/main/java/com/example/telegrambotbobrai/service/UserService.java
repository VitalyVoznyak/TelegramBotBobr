package com.example.telegrambotbobrai.service;

import com.example.telegrambotbobrai.db.UserEntity;
import com.example.telegrambotbobrai.db.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserEntityRepository userEntityRepository;
    public UserEntity saveNewUser(Message message){// Из сообщения делаем юзера
        UserEntity user = new UserEntity();
        user.setFirstName(message.getChat().getFirstName());
        user.setLastName(message.getChat().getLastName());
        user.setChatId(message.getChatId());
        user.setUserName(message.getChat().getUserName());
        userEntityRepository.save(user);
        return user;
    }
}
