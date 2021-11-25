package com.lpnu.ecoplatformserver.user.service;

import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import com.lpnu.ecoplatformserver.user.dto.AuthenticationResponseDto;
import com.lpnu.ecoplatformserver.user.dto.ChangePasswordRequestDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;

import java.util.Optional;

public interface IAuthService {

    Long register(UserDto registrationDto);

    UserEntity registerFirstOrganisationUser(UserDto userDto, OrganisationEntity organisationEntity);

    AuthenticationResponseDto login(UserLoginDto loginDto);

    void changePassword(ChangePasswordRequestDto changePasswordRequestDto);

}
