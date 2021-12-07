package com.lpnu.ecoplatformserver.sensor.service.impl;

import com.lpnu.ecoplatformserver.exception.EntityNotFoundException;
import com.lpnu.ecoplatformserver.organisation.repository.OrganisationRepository;
import com.lpnu.ecoplatformserver.security.OrganisationUser;
import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionCalculationResultDto;
import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionDataDto;
import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionSensorDto;
import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionDataEntity;
import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionSensorEntity;
import com.lpnu.ecoplatformserver.sensor.mapper.IAirPollutionDataMapper;
import com.lpnu.ecoplatformserver.sensor.mapper.IAirPollutionSensorMapper;
import com.lpnu.ecoplatformserver.sensor.repository.AirPollutionDataRepository;
import com.lpnu.ecoplatformserver.sensor.repository.AirPollutionSensorRepository;
import com.lpnu.ecoplatformserver.sensor.service.IAirPollutionSensorService;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AirPollutionSensorService implements IAirPollutionSensorService {

    private static final AirPollutionDataDto NORMAL_QUALITY_VALUES = new AirPollutionDataDto(
            null,
            BigDecimal.valueOf(25),
            BigDecimal.valueOf(60),
            BigDecimal.valueOf(400),
            BigDecimal.valueOf(1.5),
            BigDecimal.valueOf(15)
    );

    private final AirPollutionSensorRepository airPollutionSensorRepository;
    private final AirPollutionDataRepository airPollutionDataRepository;
    private final OrganisationRepository organisationRepository;
    private final UserRepository userRepository;

    private final IAirPollutionSensorMapper airSensorMapper;
    private final IAirPollutionDataMapper airPollutionDataMapper;

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
    public AirPollutionCalculationResultDto findTheSensorWithTheWorstAirQuality() {
        var result = airPollutionDataRepository.findAllBySensor_Organisation_Id(currentUser.getOrganisationId())
                .stream()
                .collect(groupingBy(AirPollutionDataEntity::getSensor, toList()))
                .entrySet().stream()
                .map(this::mapToAverage)
                .max((p1, p2) -> compareAirPollutionData(p1.getSecond(), p2.getSecond()))
                .orElseThrow(() -> new EntityNotFoundException("No sensors data were found"));

        return new AirPollutionCalculationResultDto(
                airSensorMapper.mapToDto(result.getFirst()),
                airPollutionDataMapper.mapToDto(result.getSecond()),
                NORMAL_QUALITY_VALUES
        );
    }

    private int compareAirPollutionData(AirPollutionDataEntity firstData, AirPollutionDataEntity secondData) {
        return Comparator.comparing(AirPollutionDataEntity::getCo2)
                .thenComparing(AirPollutionDataEntity::getN2o)
                .thenComparing(AirPollutionDataEntity::getO3)
                .thenComparing(AirPollutionDataEntity::getSo2)
                .thenComparing(AirPollutionDataEntity::getDust)
                .compare(firstData, secondData);
    }

    private Pair<AirPollutionSensorEntity, AirPollutionDataEntity> mapToAverage(Map.Entry<AirPollutionSensorEntity, List<AirPollutionDataEntity>> inputData) {
        var sumOfData = inputData.getValue().stream().reduce(new AirPollutionDataEntity(), (orig, next) -> {
            orig.setCo2(orig.getCo2().add(next.getCo2()));
            orig.setN2o(orig.getN2o().add(next.getN2o()));
            orig.setO3(orig.getO3().add(next.getO3()));
            orig.setSo2(orig.getSo2().add(next.getSo2()));
            orig.setDust(orig.getDust().add(next.getDust()));
            return orig;
        });

        var totalDataCount = BigDecimal.valueOf(inputData.getValue().size());
        sumOfData.setCo2(sumOfData.getCo2().divide(totalDataCount, RoundingMode.CEILING));
        sumOfData.setN2o(sumOfData.getN2o().divide(totalDataCount, RoundingMode.CEILING));
        sumOfData.setO3(sumOfData.getO3().divide(totalDataCount, RoundingMode.CEILING));
        sumOfData.setSo2(sumOfData.getSo2().divide(totalDataCount, RoundingMode.CEILING));
        sumOfData.setDust(sumOfData.getDust().divide(totalDataCount, RoundingMode.CEILING));

        return Pair.of(inputData.getKey(), sumOfData);
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
