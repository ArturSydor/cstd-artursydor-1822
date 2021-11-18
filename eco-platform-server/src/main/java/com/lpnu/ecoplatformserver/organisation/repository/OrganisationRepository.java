package com.lpnu.ecoplatformserver.organisation.repository;

import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<OrganisationEntity, Long> {
}
