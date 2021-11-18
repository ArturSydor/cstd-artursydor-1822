package com.lpnu.ecoplatformserver.organisation.dto;

import com.lpnu.ecoplatformserver.user.dto.UserDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record OrganisationDto(
        Long id,
        @NotNull String name,
        @Email String email,
        boolean isMemberApprovalRequired,
        @NotNull UserDto creator) {
}
