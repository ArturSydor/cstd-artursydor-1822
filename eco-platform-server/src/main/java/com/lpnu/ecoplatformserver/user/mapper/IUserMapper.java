package com.lpnu.ecoplatformserver.user.mapper;

import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto mapToDto(UserEntity entity);

    @Mapping(target = "joined", ignore = true)
    UserEntity mapToEntity(UserDto dto);

}
