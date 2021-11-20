package com.lpnu.ecoplatformserver.organisation.mapper;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrganisationMapper {

    OrganisationEntity mapToEntity(OrganisationDto dto);

    OrganisationDto mapToDto(OrganisationEntity entity);

    OrganisationSimpleDto mapToSimpleDto(OrganisationEntity entity);

}
