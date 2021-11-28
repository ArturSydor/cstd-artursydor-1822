package com.lpnu.ecoplatformserver.eco_project.repository;

import com.lpnu.ecoplatformserver.eco_project.entity.UserProjectPointsEntity;
import com.lpnu.ecoplatformserver.eco_project.entity.UserProjectPointsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectPointsRepository extends JpaRepository<UserProjectPointsEntity, UserProjectPointsId> {
}
