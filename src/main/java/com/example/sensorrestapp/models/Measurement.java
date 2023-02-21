package com.example.sensorrestapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Entity
@Data       // нужны только Getter/Setter
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;     // исп класс обвертку

    @NotNull
    @Range(min = -100, max = 100,message = "Значение от -100 до 100")
    @Column(name = "value")
    private Double value;        // исп класс обвертку Double, так как у double нет Null (присв 0.0) а это не корр данные

    @NotNull
    @Column(name = "raining")
    private Boolean raining;

    @NotNull
    @Column(name = "measurement_date_time")
    private LocalDateTime measurementDateTime;

    @NotNull
    @ManyToOne          // тип связи
    @JoinColumn(name = "sensor", referencedColumnName = "name") // указываем по какой колонке
    private Sensor sensor;
}
