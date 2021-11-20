package com.lpnu.ecoplatformserver.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record AuthenticationResponseDto(
        @NotNull @NotEmpty String authenticationToken,
        @NotNull @NotEmpty String email
) {
}
