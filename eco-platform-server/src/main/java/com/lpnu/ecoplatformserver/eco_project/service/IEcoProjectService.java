package com.lpnu.ecoplatformserver.eco_project.service;

import com.lpnu.ecoplatformserver.eco_project.dto.EcoProjectDto;
import com.lpnu.ecoplatformserver.eco_project.entity.EcoProjectEntity;

import java.util.List;

public interface IEcoProjectService {

    List<EcoProjectDto> getAll();

    EcoProjectEntity findOne(Long id);

    EcoProjectDto getOne(Long id);

    void create(EcoProjectDto projectDto);

    EcoProjectDto update(EcoProjectDto projectDto);

    void delete(Long id);

}
