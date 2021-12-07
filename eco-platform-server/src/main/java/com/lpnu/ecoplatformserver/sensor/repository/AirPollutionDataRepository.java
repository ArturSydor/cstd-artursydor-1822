package com.lpnu.ecoplatformserver.sensor.repository;

import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirPollutionDataRepository extends JpaRepository<AirPollutionDataEntity, Long> {

    List<AirPollutionDataEntity> findAllBySensor_Organisation_Id(Long organisationId);

}
