package com.example.telegrambotbobrai.service;

import com.example.telegrambotbobrai.dto.WeatherResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final String apiKey = "af776a5691a775aa2f41b4f052e397e5";

    public String getWeatherInfoByCityName(String cityName) {
        //Спрашиваем погоду у Api
        WeatherResponseDTO weatherInfoDto = getWeatherFromApi(cityName);

        //Вытаскиваем интересующие нас данные из ответа API
        float temp = weatherInfoDto.main.temp;
        float feelsLike = weatherInfoDto.main.feelsLike;
        String description = weatherInfoDto.weather.get(0) == null ? null : weatherInfoDto.weather.get(0).getDescription();
        float humidity = weatherInfoDto.main.humidity;
        float windSpeed = weatherInfoDto.wind.speed;

        //Формируем человекочитаемый ответ пользователю
        return
                "Темература: " + temp + "\n" +
                        "Ощущаемая температура: " + feelsLike + "\n" +
                        "Описание погоды: " + description + "\n" +
                        "Влажность: " + humidity + "\n" +
                        "Скорость ветра: " + windSpeed;
    }

    private WeatherResponseDTO getWeatherFromApi(String cityName) throws RuntimeException {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://api.openweathermap.org/data/2.5/weather?appid=" + apiKey + "&q=" + cityName;
        return restTemplate.getForObject(resourceUrl, WeatherResponseDTO.class);
    }
}
