CREATE TABLE Measurements
(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    value DOUBLE PRECISION NOT NULL,
    raining BOOLEAN NOT NULL,
    measurement_date_time timestamp not null,
    sensor varchar(30) references Sensors(name)
);