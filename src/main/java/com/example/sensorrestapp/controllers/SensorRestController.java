package com.example.sensorrestapp.controllers;

import com.example.sensorrestapp.dto.SensorDto;
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

import java.sql.SQLException;

import static com.example.sensorrestapp.util.ErrorsUtil.returnErrorsToClient;


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

        Sensor sensor = modelMapper.map(sensorDto, Sensor.class);
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        sensorService.create(sensor);    // можем делать mapping в Service или тут
        return ResponseEntity.ok(HttpStatus.OK);
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
