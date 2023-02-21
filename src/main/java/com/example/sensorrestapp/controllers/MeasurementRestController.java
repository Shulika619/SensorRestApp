package com.example.sensorrestapp.controllers;

import com.example.sensorrestapp.dto.MeasurementResponse;
import com.example.sensorrestapp.dto.MeasurementsDto;
import com.example.sensorrestapp.models.Measurement;
import com.example.sensorrestapp.services.MeasurementService;
import com.example.sensorrestapp.util.MeasurementValidator;
import com.example.sensorrestapp.util.SensorErrorResponse;
import com.example.sensorrestapp.util.SensorException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.example.sensorrestapp.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementRestController {

    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementsDto measurementsDto, BindingResult bindingResult) {

        Measurement measurement = modelMapper.map(measurementsDto, Measurement.class); // можем делать mapping в Service или тут
        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        measurementService.create(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public MeasurementResponse getMeasurements(){
        // можно было сразу вернуть List<MeasurementsDto> но хорошим тоном явл оборачивание в обьект
        return new MeasurementResponse(measurementService.findAll().stream()
                .map(measurement ->  modelMapper.map(measurement, MeasurementsDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        return measurementService.findAll().stream().filter(Measurement::getRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST); //400
    }
}
