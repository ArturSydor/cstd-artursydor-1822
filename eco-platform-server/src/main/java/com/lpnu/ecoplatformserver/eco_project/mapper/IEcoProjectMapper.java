package com.lpnu.ecoplatformserver.eco_project.mapper;

import com.lpnu.ecoplatformserver.eco_project.dto.EcoProjectDto;
import com.lpnu.ecoplatformserver.eco_project.entity.EcoProjectEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEcoProjectMapper {

    EcoProjectDto mapToDto(EcoProjectEntity projectEntity);

    EcoProjectEntity mapToEntity(EcoProjectDto projectDto);

}
