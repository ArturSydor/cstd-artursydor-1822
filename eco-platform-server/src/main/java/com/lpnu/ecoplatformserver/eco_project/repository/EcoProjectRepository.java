package com.lpnu.ecoplatformserver.eco_project.repository;

import com.lpnu.ecoplatformserver.eco_project.entity.EcoProjectEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EcoProjectRepository extends CrudRepository<EcoProjectEntity, Long> {

    Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "created", "published");

    List<EcoProjectEntity> findAllByOrganisation_IdAndCreator_IdOrPublishedTrue(Long organisationId, Long creatorId, Sort sort);

    List<EcoProjectEntity> findAllByCreator_Id(Long creatorId, Sort sort);

    Optional<EcoProjectEntity> findByName(String name);
}
