package com.lpnu.ecoplatformserver.config;

import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionDataDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<Integer, AirPollutionDataDto> airPollutionDataConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new IntegerDeserializer(), new JsonDeserializer<>(AirPollutionDataDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, AirPollutionDataDto> airPollutionDataContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, AirPollutionDataDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(airPollutionDataConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

}