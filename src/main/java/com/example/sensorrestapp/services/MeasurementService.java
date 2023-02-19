package com.example.sensorrestapp.services;

import com.example.sensorrestapp.models.Measurement;
import com.example.sensorrestapp.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Transactional
    public void create(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }
    private void enrichMeasurement(Measurement measurement) {
        // сами находим сенсор из бд по имени и вставить обьект из Hibernate persistance context'а, без этого будет ошибка
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setMeasurementDateTime(LocalDateTime.now());
    }

    @Transactional
    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

}
