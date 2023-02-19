package com.example.sensorrestapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "Sensors")
public class Sensor implements Serializable {   // Если связь не по id, а по значению (name) нужно указывать - implements Serializable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;         // использ класс обвертку Integer, так как у int нет Null (присв 0)

    @NotBlank
    @Size(min = 3, max = 30, message = "Название серсора должно быть 3-30 символов")
    @Column(name = "name")
    private String name;
}
