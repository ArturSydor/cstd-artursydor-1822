package com.lpnu.ecoplatformserver.user.dto;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public record UserDto(
        Long id,
        @NotEmpty @Length(min = 1) String firstName,
        @NotEmpty @Length(min = 1) String lastName,
        @NotEmpty @Email String email,
        String password,
        boolean active,
        boolean deleted,
        int availablePoints,
        LocalDateTime joined,
        OrganisationSimpleDto organisation,
        @Valid RoleDto role
) {
}
