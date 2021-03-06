package com.lpnu.ecoplatformserver.user.repository;

import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByOrganisation_IdAndActiveFalse(Long organisationId);

    List<UserEntity> findAllByActiveIsTrue();

}
