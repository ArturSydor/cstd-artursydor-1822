package com.lpnu.ecoplatformserver.sensor.mapper;

import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionDataDto;
import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionDataEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAirPollutionDataMapper {

    AirPollutionDataEntity mapToEntity(AirPollutionDataDto dto);

    AirPollutionDataDto mapToDto(AirPollutionDataEntity entity);

}
