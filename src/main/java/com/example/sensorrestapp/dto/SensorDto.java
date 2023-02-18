package com.example.sensorrestapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

//@Value
@Getter
@Setter
public class SensorDto {
    @Size(min = 3, max = 30, message = "Название серсора должно быть 3-30 символов")
    @NotBlank
    private String name;
}
