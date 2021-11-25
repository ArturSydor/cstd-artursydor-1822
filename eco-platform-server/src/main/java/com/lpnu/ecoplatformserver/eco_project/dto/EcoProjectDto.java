package com.lpnu.ecoplatformserver.eco_project.dto;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public record EcoProjectDto(
        Long id,
        @NotEmpty String name,
        @NotEmpty String description,
        int points,
        @Min(1) int maxAllowedPointsPerUser,
        boolean published,
        boolean closed,
        LocalDateTime created,
        UserDto creator,
        OrganisationSimpleDto organisation
) {
}
