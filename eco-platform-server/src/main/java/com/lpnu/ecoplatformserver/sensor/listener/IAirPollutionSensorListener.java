package com.lpnu.ecoplatformserver.sensor.listener;

import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionDataDto;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface IAirPollutionSensorListener {

    void process(@Payload AirPollutionDataDto message, Acknowledgment acknowledgment);

}
