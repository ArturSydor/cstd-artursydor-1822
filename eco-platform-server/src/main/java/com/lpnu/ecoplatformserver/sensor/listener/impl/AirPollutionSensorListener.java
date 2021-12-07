package com.lpnu.ecoplatformserver.sensor.listener.impl;

import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionDataDto;
import com.lpnu.ecoplatformserver.sensor.listener.IAirPollutionSensorListener;
import com.lpnu.ecoplatformserver.sensor.mapper.IAirPollutionDataMapper;
import com.lpnu.ecoplatformserver.sensor.repository.AirPollutionDataRepository;
import com.lpnu.ecoplatformserver.sensor.repository.AirPollutionSensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class AirPollutionSensorListener implements IAirPollutionSensorListener {

    private static final String LISTENER_NAME = "airPollutionDataListener";

    private final AirPollutionSensorRepository airPollutionSensorRepository;
    private final AirPollutionDataRepository airPollutionDataRepository;

    private final IAirPollutionDataMapper airPollutionDataMapper;

    @Override
    @KafkaListener(id = LISTENER_NAME,
            topics = "#{'${sensor.air-pollution.topic}'}",
            groupId = "#{'${sensor.air-pollution.consumer.group}'}",
            containerFactory = "airPollutionDataContainerFactory")
    public void process(AirPollutionDataDto message, Acknowledgment acknowledgment) {
        log.debug("AirPollutionSensorListener received message: {}", message);

        try{
            var airPollutionData = airPollutionDataMapper.mapToEntity(message);
            airPollutionData.setSensor(airPollutionSensorRepository.getById(message.sensorId()));
            airPollutionDataRepository.save(airPollutionData);
        } catch (Exception e) {
            log.error("Unexpected error whicle saving message: " + message, e);
        }

        acknowledgment.acknowledge();

        log.debug("AirPollutionSensorListener finished message processing sensorId[{}]", message.sensorId());
    }



}
