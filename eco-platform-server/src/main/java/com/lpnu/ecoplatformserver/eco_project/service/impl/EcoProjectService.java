package com.lpnu.ecoplatformserver.eco_project.service.impl;

import com.lpnu.ecoplatformserver.eco_project.dto.EcoProjectDto;
import com.lpnu.ecoplatformserver.eco_project.entity.EcoProjectEntity;
import com.lpnu.ecoplatformserver.eco_project.mapper.IEcoProjectMapper;
import com.lpnu.ecoplatformserver.eco_project.repository.EcoProjectRepository;
import com.lpnu.ecoplatformserver.eco_project.service.IEcoProjectService;
import com.lpnu.ecoplatformserver.exception.DuplicatedEntryException;
import com.lpnu.ecoplatformserver.exception.EntityNotFoundException;
import com.lpnu.ecoplatformserver.exception.ObjectAccessDeniedException;
import com.lpnu.ecoplatformserver.exception.PublishedEntityModificationException;
import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import com.lpnu.ecoplatformserver.organisation.repository.OrganisationRepository;
import com.lpnu.ecoplatformserver.security.OrganisationUser;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EcoProjectService implements IEcoProjectService {

    private final OrganisationUser currentUser;

    private final EcoProjectRepository ecoProjectRepository;
    private final UserRepository userRepository;
    private final OrganisationRepository organisationRepository;

    private final IEcoProjectMapper ecoProjectMapper;

    @Override
    public List<EcoProjectDto> getAll() {
        return ecoProjectRepository.findAllByOrganisation_IdAndCreator_IdOrPublishedTrue(currentUser.getOrganisationId(), currentUser.getUserId())
                .stream()
                .map(ecoProjectMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EcoProjectEntity findOne(Long id) {
        Objects.requireNonNull(id);
        return ecoProjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Eco project with id=%s was not found!", id));
    }

    @Override
    public EcoProjectDto getOne(Long id) {
        return ecoProjectMapper.mapToDto(findOne(id));
    }

    @Override
    public void create(EcoProjectDto projectDto) {
        Objects.requireNonNull(projectDto);
        ecoProjectRepository.findByName(projectDto.name()).ifPresent(project -> checkProjectUniqueNamePerOrganisation(project, projectDto));

        EcoProjectEntity entity = ecoProjectMapper.mapToEntity(projectDto);

        UserEntity user = userRepository.getById(currentUser.getUserId());
        OrganisationEntity organisation = organisationRepository.getById(currentUser.getOrganisationId());
        entity.setCreator(user);
        entity.setOrganisation(organisation);

        ecoProjectRepository.save(entity);
    }

    @Override
    public EcoProjectDto update(EcoProjectDto projectDto) {
        Objects.requireNonNull(projectDto);

        EcoProjectEntity existingEntity = findOne(projectDto.id());
        checkCreator(projectDto.creator().id());
        checkPublishState(existingEntity.isPublished());
        ecoProjectRepository.findByName(projectDto.name()).ifPresent(entity -> checkProjectUniqueNamePerOrganisation(entity, projectDto));
        checkPointsNotUpdated(existingEntity, projectDto);

        EcoProjectEntity entityForUpdate = ecoProjectMapper.mapToEntity(projectDto);
        return ecoProjectMapper.mapToDto(ecoProjectRepository.save(entityForUpdate));
    }

    @Override
    public void delete(Long id) {
        EcoProjectEntity entity = findOne(id);
        checkPublishState(entity.isPublished());
        checkCreator(entity.getCreator().getId());
        ecoProjectRepository.delete(entity);
    }

    private void checkPointsNotUpdated(EcoProjectEntity entity, EcoProjectDto dto) {
        if (entity.getPoints() != dto.points()) {
            throw new IllegalStateException("Points field modification is not allowed in this method!");
        }
    }

    private void checkProjectUniqueNamePerOrganisation(EcoProjectEntity projectEntity, EcoProjectDto projectDto) {
        if (Objects.equals(projectEntity.getName(), projectDto.name()) &&
                !Objects.equals(projectEntity.getId(), projectDto.id()) &&
                Objects.equals(projectEntity.getOrganisation().getId(), currentUser.getOrganisationId())) {
            throw new DuplicatedEntryException("Project with name=[%s] already exists!", projectDto.name());
        }
    }

    /**
     * Checks project creator. If current user is not project creator, then exception will be thrown.
     *
     * @throws @ObjectAccessDenied
     */
    private void checkCreator(Long creatorId) {
        if (!Objects.equals(creatorId, currentUser.getUserId())) {
            throw new ObjectAccessDeniedException("Operation is not allowed for user %s", currentUser.getUsername());
        }
    }

    /**
     * Checks project's publish state. If object is published, then exception will be thrown.
     *
     * @throws @PublishedEntityModificationException
     */
    private void checkPublishState(boolean isPublished) {
        if (isPublished) {
            throw new PublishedEntityModificationException("Any modification is not allowed for published projects");
        }
    }
}
