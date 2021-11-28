package com.lpnu.ecoplatformserver.eco_project.mapper;

import com.lpnu.ecoplatformserver.eco_project.dto.EcoProjectDto;
import com.lpnu.ecoplatformserver.eco_project.entity.EcoProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IEcoProjectMapper {

    EcoProjectDto mapToDto(EcoProjectEntity projectEntity);

    @Mapping(target = "created", ignore = true)
    EcoProjectEntity mapToEntity(EcoProjectDto projectDto);

}
