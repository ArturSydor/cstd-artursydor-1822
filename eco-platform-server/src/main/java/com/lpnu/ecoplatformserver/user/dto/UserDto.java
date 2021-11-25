package com.lpnu.ecoplatformserver.user.dto;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public record UserDto(
        Long id,
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotEmpty @Email String email,
        @NotEmpty String password,
        boolean active,
        boolean deleted,
        LocalDateTime joined,
        OrganisationSimpleDto organisation,
        @Valid RoleDto role
) {
}
