package com.lpnu.ecoplatformserver.eco_project.repository;

import com.lpnu.ecoplatformserver.eco_project.entity.EcoProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EcoProjectRepository extends JpaRepository<EcoProjectEntity, Long> {

    List<EcoProjectEntity> findAllByOrganisation_IdAndCreator_IdOrPublishedTrue(Long organisationId, Long creatorId);

    Optional<EcoProjectEntity> findByName(String name);
}
