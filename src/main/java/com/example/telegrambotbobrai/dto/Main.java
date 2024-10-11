
package com.example.telegrambotbobrai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class Main {

    public Float temp;
    @JsonProperty("feels_like")
    public Float feelsLike;
    public Float tempMin;
    public Float tempMax;
    public Integer pressure;
    public Integer humidity;
    public Integer seaLevel;
    public Integer grndLevel;

}
