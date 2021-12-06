package com.lpnu.ecoplatformserver.sensor.dto;

import com.lpnu.ecoplatformserver.common.constants.RegexPatterns;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record AirPollutionSensorDto(
        Long id,
        @NotBlank String externalIdentifier,
        @NotBlank @Pattern(regexp = RegexPatterns.LATITUDE) String latitude,
        @NotBlank @Pattern(regexp = RegexPatterns.LONGITUDE) String longitude,
        OrganisationSimpleDto organisation,
        UserDto creator
) {
}
