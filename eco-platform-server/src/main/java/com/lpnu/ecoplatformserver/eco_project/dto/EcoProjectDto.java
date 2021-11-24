package com.lpnu.ecoplatformserver.eco_project.dto;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;

import javax.validation.constraints.NotEmpty;

public record EcoProjectDto(
        Long id,
        @NotEmpty String name,
        @NotEmpty String description,
        int points,
        int maxAllowedPointsPerUser,
        boolean published,
        boolean closed,
        UserDto creator,
        OrganisationSimpleDto organisation
) {
}
