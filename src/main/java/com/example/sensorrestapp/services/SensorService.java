package com.example.sensorrestapp.services;

import com.example.sensorrestapp.models.Sensor;
import com.example.sensorrestapp.repositories.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Transactional
    public void create(Sensor sensor) {
        log.info("IN SensorService create: {}", sensor);
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String name){
        log.info("IN SensorService findByName: {}",name);
        return sensorRepository.findByName(name);
    }

    public List<Sensor> findAll(){
        log.info("IN SensorService findAll");
        return sensorRepository.findAll();
    }
    public Optional<Sensor> findById(int id){
        log.info("IN SensorService findById {}",id);
        return sensorRepository.findById(id);
    }

    @Transactional
    public void update(int id, Sensor sensor){
        log.info("IN SensorService update id{}, sesor {}", id,sensor);
        sensorRepository.findById(id).get().setName(sensor.getName());
    }

    @Transactional
    public void delete(int id){
        log.info("IN SensorService delete id {}", id);
        sensorRepository.deleteById(id);
    }

//    private void enrichSensor(Person person) {
//          // TODO: add some data (LocalDate())
//    }
}
