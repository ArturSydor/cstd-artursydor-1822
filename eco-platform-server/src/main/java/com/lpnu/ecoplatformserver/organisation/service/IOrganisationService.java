package com.lpnu.ecoplatformserver.organisation.service;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;

import java.util.Set;

public interface IOrganisationService {

    OrganisationDto getOne(Long id);

    OrganisationEntity findById(Long id);

    Set<OrganisationSimpleDto> getAll();

    void create(OrganisationDto newOrganisation);

    OrganisationDto update(OrganisationDto organisation);

    void delete(Long id);

}
