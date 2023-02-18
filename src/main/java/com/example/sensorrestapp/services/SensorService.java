package com.example.sensorrestapp.services;

import com.example.sensorrestapp.dto.SensorDto;
import com.example.sensorrestapp.models.Sensor;
import com.example.sensorrestapp.repositories.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void create(SensorDto sensorDto) {
        Sensor sensor = modelMapper.map(sensorDto, Sensor.class);
        sensorRepository.save(sensor);
    }

//    private void enrichSensor(Person person) {
//          // TODO: add some data
//    }
}
