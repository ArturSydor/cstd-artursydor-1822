package com.lpnu.ecoplatformserver.organisation.service.impl;

import com.lpnu.ecoplatformserver.exception.EntityNotFoundException;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import com.lpnu.ecoplatformserver.organisation.mapper.IOrganisationMapper;
import com.lpnu.ecoplatformserver.organisation.repository.OrganisationRepository;
import com.lpnu.ecoplatformserver.organisation.service.IOrganisationService;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import com.lpnu.ecoplatformserver.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrganisationService implements IOrganisationService {

    private final IUserService userService;

    private final OrganisationRepository organisationRepository;

    private final IOrganisationMapper organisationMapper;

    @Override
    public OrganisationDto getOne(Long id) {
        return organisationMapper.mapToDto(findById(id));
    }

    @Override
    public OrganisationEntity findById(Long id) {
        Objects.requireNonNull(id);
        return organisationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organisation with id=%s not exists", id));
    }

    @Override
    public Set<OrganisationSimpleDto> getAll() {
        return organisationRepository.findAll()
                .stream()
                .map(organisationMapper::mapToSimpleDto)
                .collect(toSet());
    }

    @Override
    public void create(OrganisationDto newOrganisation) {
        Objects.requireNonNull(newOrganisation);
        OrganisationEntity organisationForSave = organisationMapper.mapToEntity(newOrganisation);
        UserEntity creator = organisationForSave.getCreator();
        organisationForSave.setCreator(null);

        OrganisationEntity savedOrganisation = organisationRepository.save(organisationForSave);
        creator.setOrganisation(savedOrganisation);
        savedOrganisation.setCreator(userService.createUser(creator));
        organisationRepository.save(savedOrganisation);
    }

    @Override
    public OrganisationDto update(OrganisationDto organisation) {
        Objects.requireNonNull(organisation);
        OrganisationEntity organisationEntity = organisationMapper.mapToEntity(organisation);
        return organisationMapper.mapToDto(organisationRepository.save(organisationEntity));
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id);
        OrganisationEntity organisation = findById(id);
        organisation.setDeleted(Boolean.TRUE);
        organisationRepository.save(organisation);
    }

}
