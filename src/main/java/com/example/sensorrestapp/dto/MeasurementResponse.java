package com.example.sensorrestapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MeasurementResponse {
    private List<MeasurementsDto> measurements;

}
