package com.example.sensorrestapp.dto;

import com.example.sensorrestapp.models.Sensor;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Getter
@Setter
public class MeasurementsDto {

    @NotNull
    @Range(min = -100, max = 100,message = "Значение от -100 до 100")
    @Column(name = "value")
    private Double value;        // исп класс обвертку Double, так как у double нет Null (присв 0.0) а это не корр данные

    @NotNull
    @Column(name = "raining")
    private Boolean raining;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;
}
