package com.example.telegrambotbobrai.utils;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiListResponse <T>{
    @NotNull
    private List<T> result;
    @NotNull
    private Integer total;
}