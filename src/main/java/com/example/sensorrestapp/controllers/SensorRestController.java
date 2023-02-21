package com.example.sensorrestapp.controllers;


import com.example.sensorrestapp.dto.SensorDto;
import com.example.sensorrestapp.dto.SensorResponse;
import com.example.sensorrestapp.models.Sensor;
import com.example.sensorrestapp.services.SensorService;
import com.example.sensorrestapp.util.SensorErrorResponse;
import com.example.sensorrestapp.util.SensorException;
import com.example.sensorrestapp.util.SensorValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.example.sensorrestapp.util.ErrorsUtil.returnErrorsToClient;
import static org.springframework.http.ResponseEntity.noContent;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sensors")
public class SensorRestController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @PostMapping("/registration")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDto sensorDto, BindingResult bindingResult) {

        Sensor sensor = modelMapper.map(sensorDto, Sensor.class);    // можем делать mapping в Service или тут
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        sensorService.create(sensor);
        return ResponseEntity.ok(HttpStatus.CREATED);   // 200 "CREATED"
    }

    @GetMapping
    public SensorResponse findAll() {
        return new SensorResponse(sensorService.findAll()
                .stream().map(sensor -> modelMapper.map(sensor, SensorDto.class))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDto> findById(@PathVariable("id") int id) {
        SensorDto sensorDto = modelMapper.map(sensorService.findById(id), SensorDto.class);
        System.out.println(sensorDto);
        if (sensorDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404

        return new ResponseEntity<>(sensorDto, HttpStatus.OK);  // 200
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable int id,
                                             @RequestBody @Valid SensorDto sensorDto,
                                             BindingResult bindingResult) {

        Sensor sensor = modelMapper.map(sensorDto, Sensor.class);    // можем делать mapping в Service или тут
        // TODO: добавить валидацию для id (есть ли такой?)
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        sensorService.update(id, sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        // TODO: валидация id (есть ли такой)
        sensorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST); //400
    }

    //  Обработка исключений SQL 23505 - не укникальное значение в name (такой польз уже сущ)
    // Или делаем SensorValidator и +1 запрос к БД, проверяем есть ли такой, если есть, кид исключ
//    @ExceptionHandler
//    private ResponseEntity<SensorErrorResponse> handleException(SQLException e) {
//        SensorErrorResponse response = new SensorErrorResponse(
//                e.getSQLState().equals("23505") ? "Такой датчик уже существует!" : "Ошибка БД",
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity(response, HttpStatus.BAD_REQUEST); //400
//    }
}
