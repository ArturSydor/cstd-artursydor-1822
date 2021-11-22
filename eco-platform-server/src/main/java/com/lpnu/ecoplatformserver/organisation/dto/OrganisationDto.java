package com.lpnu.ecoplatformserver.organisation.dto;

import com.lpnu.ecoplatformserver.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record OrganisationDto(
        Long id,
        @NotEmpty String name,
        @NotEmpty @Email String email,
        boolean memberApprovalRequired,
        boolean deleted,
        @Valid UserDto creator) {
}
