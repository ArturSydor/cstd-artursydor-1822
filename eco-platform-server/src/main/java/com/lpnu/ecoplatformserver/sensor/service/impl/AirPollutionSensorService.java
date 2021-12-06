package com.lpnu.ecoplatformserver.sensor.service.impl;

import com.lpnu.ecoplatformserver.exception.EntityNotFoundException;
import com.lpnu.ecoplatformserver.organisation.repository.OrganisationRepository;
import com.lpnu.ecoplatformserver.security.OrganisationUser;
import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionSensorDto;
import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionSensorEntity;
import com.lpnu.ecoplatformserver.sensor.mapper.IAirSensorMapper;
import com.lpnu.ecoplatformserver.sensor.repository.AirPollutionSensorRepository;
import com.lpnu.ecoplatformserver.sensor.service.IAirPollutionSensorService;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AirPollutionSensorService implements IAirPollutionSensorService {

    private final AirPollutionSensorRepository airPollutionSensorRepository;
    private final OrganisationRepository organisationRepository;
    private final UserRepository userRepository;

    private final IAirSensorMapper airSensorMapper;

    private final OrganisationUser currentUser;

    @Override
    public List<AirPollutionSensorDto> getAllSensors() {
        return airPollutionSensorRepository.findAllByOrganisation_Id(currentUser.getOrganisationId())
                .stream()
                .map(airSensorMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AirPollutionSensorDto getOne(Long sensorId) {
        return airSensorMapper.mapToDto(findOne(sensorId));
    }

    @Override
    public AirPollutionSensorEntity findOne(Long sensorId) {
        Objects.requireNonNull(sensorId);
        return airPollutionSensorRepository.findById(sensorId)
                .orElseThrow(() -> new EntityNotFoundException("Air sensor with id=%s not found", sensorId));
    }

    @Override
    public AirPollutionSensorEntity findOneByExternalIdentifier(String identifier) {
        return airPollutionSensorRepository.findByExternalIdentifier(identifier)
                .orElseThrow(() -> new EntityNotFoundException("Air sensor with identifier=%s not found", identifier));
    }

    @Override
    public AirPollutionSensorDto create(AirPollutionSensorDto newSensor) {
        Objects.requireNonNull(newSensor);
        var newAirSensor = airSensorMapper.mapToEntity(newSensor);
        newAirSensor.setOrganisation(organisationRepository.getById(currentUser.getOrganisationId()));
        newAirSensor.setCreator(userRepository.getById(currentUser.getUserId()));
        return airSensorMapper.mapToDto(airPollutionSensorRepository.save(newAirSensor));
    }

    @Override
    public AirPollutionSensorDto update(AirPollutionSensorDto sensor) {
        Objects.requireNonNull(sensor);
        var airSensor = findOne(sensor.id());
        airSensor.setLatitude(sensor.latitude());
        airSensor.setLongitude(sensor.longitude());
        return airSensorMapper.mapToDto(airPollutionSensorRepository.save(airSensor));
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id);
        airPollutionSensorRepository.deleteById(id);
    }
}
