package com.lpnu.ecoplatformserver.user.dto;

import javax.validation.constraints.NotEmpty;

public record RoleDto(
        Integer id,
        @NotEmpty String name
) {
}
