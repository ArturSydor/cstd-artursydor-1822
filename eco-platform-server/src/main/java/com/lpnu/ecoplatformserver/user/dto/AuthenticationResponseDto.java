package com.lpnu.ecoplatformserver.user.dto;

import javax.validation.constraints.NotEmpty;

public record AuthenticationResponseDto(
        @NotEmpty String authenticationToken,
        @NotEmpty String email,
        @NotEmpty String role
) {
}
