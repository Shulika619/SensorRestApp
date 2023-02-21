package com.example.sensorrestapp.dto;

import com.example.sensorrestapp.models.Sensor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SensorResponse {
    private List<SensorDto> sensors;

}
