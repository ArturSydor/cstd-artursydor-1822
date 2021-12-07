package com.lpnu.ecoplatformserver.sensor.dto;

import java.math.BigDecimal;

public record AirPollutionDataDto(
        Long sensorId,
        BigDecimal n2o,
        BigDecimal o3,
        BigDecimal co2,
        BigDecimal so2,
        BigDecimal dust
) {
}
