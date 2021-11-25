package com.lpnu.ecoplatformserver.organisation.mapper;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrganisationMapper {

    @Mapping(target = "created", ignore = true)
    OrganisationEntity mapToEntity(OrganisationDto dto);

    OrganisationDto mapToDto(OrganisationEntity entity);

    OrganisationSimpleDto mapToSimpleDto(OrganisationEntity entity);

}
