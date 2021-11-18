package com.lpnu.ecoplatformserver.user.repository;

import com.lpnu.ecoplatformserver.user.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
