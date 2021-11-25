package com.lpnu.ecoplatformserver.eco_project.dto;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public record EcoProjectDto(
        Long id,
        @NotEmpty @Length(min = 1) String name,
        @NotEmpty @Length(min = 1) String description,
        int points,
        @Min(1) int maxAllowedPointsPerUser,
        boolean published,
        boolean closed,
        LocalDateTime created,
        UserDto creator,
        OrganisationSimpleDto organisation
) {
}
