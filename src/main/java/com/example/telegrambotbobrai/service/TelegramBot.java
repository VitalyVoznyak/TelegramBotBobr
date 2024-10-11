package com.example.telegrambotbobrai.service;

import com.example.telegrambotbobrai.db.MessageLogEntity;
import com.example.telegrambotbobrai.db.MessageLogEntityRepository;
import com.example.telegrambotbobrai.db.UserEntity;
import com.example.telegrambotbobrai.db.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final UserEntityRepository userEntityRepository;
    private final MessageLogEntityRepository messageLogEntityRepository;
    private final WeatherService weatherService;
    private final UserService userService;

    public TelegramBot(WeatherService weatherService,
                       MessageLogEntityRepository messageLogEntityRepository, UserEntityRepository userEntityRepository, UserService userService) {
        super("7743641063:AAHvEeLX2Z8wQHk7vUsXOYAyXVCrC7p_wX4");// Пока что не будем выносить токен в пропертя
        this.weatherService = weatherService;
        this.messageLogEntityRepository = messageLogEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.userService = userService;
    }

    @Override // ПЕРЕХВАТЧИК СООБЩЕНИЙ ОТ ПОЛЬЗОВАТЕЛЯ
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
        updates.forEach(update -> {
            if (update.getMessage() != null) {
                Long chatId = update.getMessage().getChatId();

                //Смотрим, есть ли такой юзер уже в базе: Если нет - создаем
                Optional<UserEntity> optionalUserEntity = userEntityRepository.findByChatId(chatId);
                UserEntity user = optionalUserEntity.orElseGet(() -> userService.saveNewUser(update.getMessage()));


                //Берем текст сообщения.
                String messageText = update.getMessage().getText();

                //Команда /start - дефолт для начала диалога с ботом
                if (Objects.equals(messageText, "/start")) {
                    //Пчриветствуем, объясняем юзеру как пользоваться ботом
                    String botResponse = "Добрый день, " + update.getMessage().getFrom().getFirstName() + "!\n" + "Используйте команду /weather <Название города на английском> чтобы узнать погоду в нем";
                    sendMessage(chatId, botResponse);
                    //Сохраняем сообщение с приветствием
                    saveLogToDataBase(user, messageText, botResponse);
                    return;
                }

                //Обрабатываем команду "/weather ".
                if (messageText.startsWith("/weather ") && !messageText.substring(9).isBlank()) {
                    String cityName = messageText.substring(9).trim();
                    String weatherInfo = weatherService.getWeatherInfoByCityName(cityName);
                    //Отправляем ответ
                    sendMessage(chatId, weatherInfo);
                    //Сохраняем новые сообщения в базу
                    saveLogToDataBase(user, messageText, weatherInfo);
                } else {
                    sendMessage(update.getMessage().getChatId(), "Команда не распознана");
                }
            }
        });
    }

    //Этот метод логирует информацию о сообщениях в бд
    private void saveLogToDataBase(UserEntity user , String userMessageText, String botResponse) {
        MessageLogEntity messageLogEntity = new MessageLogEntity(
                user,
                LocalDateTime.now(),
                botResponse,
                userMessageText
        );
        messageLogEntityRepository.save(messageLogEntity);
    }

    //Хотелось бы вынести отправку сообщений в отдельный сервис, Но метод execute(message) можно вызвать только в бот-классе
    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    //Объясняем боту, как его зовут(тоже пока не будем выносить)
    @Override
    public String getBotUsername() {
        return "BobrAi178Bot";
    }

    // А этот перехватчик нас не интересует.
    @Override
    public void onUpdateReceived(Update update) {
    }
}
