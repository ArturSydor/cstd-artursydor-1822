package com.lpnu.ecoplatformserver.user.dto;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record UserDto(
        Long id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull @Email String email,
        boolean active,
        boolean deleted,
        OrganisationSimpleDto organisation,
        RoleDto role
) {
}
