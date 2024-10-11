
package com.example.telegrambotbobrai.dto;

import lombok.Data;

import java.util.List;
@Data
public class WeatherResponseDTO {

    public Coord coord;
    public List<Weather> weather;
    public String base;
    public Main main;
    public Integer visibility;
    public Wind wind;
    public Rain rain;
    public Clouds clouds;
    public Integer dt;
    public Sys sys;
    public Integer timezone;
    public Integer id;
    public String name;
    public Integer cod;

}
