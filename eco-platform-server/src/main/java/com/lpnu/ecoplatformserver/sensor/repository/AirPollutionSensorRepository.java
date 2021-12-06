package com.lpnu.ecoplatformserver.sensor.repository;

import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionSensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirPollutionSensorRepository extends JpaRepository<AirPollutionSensorEntity, Long> {

    List<AirPollutionSensorEntity> findAllByOrganisation_Id(Long organisationId);

    Optional<AirPollutionSensorEntity> findByExternalIdentifier(String externalIdentifier);

}
