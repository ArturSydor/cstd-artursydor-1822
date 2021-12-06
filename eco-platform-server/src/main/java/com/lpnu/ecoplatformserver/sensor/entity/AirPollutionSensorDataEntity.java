package com.lpnu.ecoplatformserver.sensor.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "air_pollution_sensors_data")
public class AirPollutionSensorDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id")
    private AirPollutionSensorEntity sensor;

    private LocalDateTime date = LocalDateTime.now();

    @Column(scale = 10, precision = 3, nullable = false)
    private BigDecimal n2o;

    @Column(scale = 10, precision = 3, nullable = false)
    private BigDecimal o3;

    @Column(scale = 10, precision = 3, nullable = false)
    private BigDecimal co2;

    @Column(scale = 10, precision = 3, nullable = false)
    private BigDecimal so2;

    @Column(scale = 10, precision = 3, nullable = false)
    private BigDecimal dust;

}
