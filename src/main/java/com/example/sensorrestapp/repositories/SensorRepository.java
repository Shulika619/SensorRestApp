package com.example.sensorrestapp.repositories;

import com.example.sensorrestapp.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
