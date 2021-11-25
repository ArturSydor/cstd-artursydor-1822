package com.lpnu.ecoplatformserver.user.service;

import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;

import java.util.List;

public interface IUserService {

    List<UserDto> getAllUsersJoinRequests(Long organisationId);

    UserEntity findOne(Long id);

    UserEntity findOneByEmail(String email);

    void updateUser(UserDto user);

    void activateUser(Long id);

}
