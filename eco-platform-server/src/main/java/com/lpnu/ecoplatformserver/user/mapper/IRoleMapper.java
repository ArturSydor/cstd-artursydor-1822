package com.lpnu.ecoplatformserver.user.mapper;

import com.lpnu.ecoplatformserver.user.dto.RoleDto;
import com.lpnu.ecoplatformserver.user.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoleMapper {

    RoleDto mapToDto(RoleEntity entity);

}
