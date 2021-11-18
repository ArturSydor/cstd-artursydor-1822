package com.lpnu.ecoplatformserver.user.dto;

import javax.validation.constraints.NotNull;

public record RoleDto(
        Integer id,
        @NotNull String name
) {
}
