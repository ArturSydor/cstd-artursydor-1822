package com.lpnu.ecoplatformserver.sensor.mapper;

import com.lpnu.ecoplatformserver.organisation.mapper.IOrganisationMapper;
import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionSensorDto;
import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionSensorEntity;
import com.lpnu.ecoplatformserver.user.mapper.IUserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IUserMapper.class, IOrganisationMapper.class})
public interface IAirPollutionSensorMapper {

    AirPollutionSensorDto mapToDto(AirPollutionSensorEntity entity);

    AirPollutionSensorEntity mapToEntity(AirPollutionSensorDto dto);

}
