package com.lpnu.ecoplatformserver.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record AuthenticationResponseDto(
        @NotEmpty String authenticationToken,
        @NotEmpty String email,
        @NotEmpty String role,
        @NotNull Long organisationId
) {
}
