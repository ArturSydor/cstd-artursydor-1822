package com.lpnu.ecoplatformserver.user.mapper;

import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    UserDto mapToDto(UserEntity entity);

    UserEntity mapToEntity(UserDto dto);

}
