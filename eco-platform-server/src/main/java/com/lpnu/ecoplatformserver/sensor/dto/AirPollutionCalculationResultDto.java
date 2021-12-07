package com.lpnu.ecoplatformserver.sensor.dto;

public record AirPollutionCalculationResultDto(
        AirPollutionSensorDto sensor,
        AirPollutionDataDto sensorData,
        AirPollutionDataDto norma
) {
}
