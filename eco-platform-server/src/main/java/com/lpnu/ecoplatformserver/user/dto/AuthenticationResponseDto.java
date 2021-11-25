package com.lpnu.ecoplatformserver.user.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record AuthenticationResponseDto(
        @NotEmpty String authenticationToken,
        @NotEmpty @Length(min = 1) String email,
        @NotEmpty @Length(min = 1) String role,
        @NotNull Long organisationId
) {
}
