package com.lpnu.ecoplatformserver.sensor.service;

import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionSensorDto;
import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionSensorEntity;

import java.util.List;

public interface IAirPollutionSensorService {

    List<AirPollutionSensorDto> getAllSensors();

    AirPollutionSensorDto getOne(Long sensorId);

    AirPollutionSensorEntity findOne(Long sensorId);

    AirPollutionSensorEntity findOneByExternalIdentifier(String identifier);

    AirPollutionSensorDto create(AirPollutionSensorDto newSensor);

    AirPollutionSensorDto update(AirPollutionSensorDto sensor);

    void delete(Long id);

}
