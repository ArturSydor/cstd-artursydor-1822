package com.lpnu.ecoplatformserver.user.dto;

import javax.validation.constraints.NotEmpty;

public record UserLoginDto(
        @NotEmpty String email,
        @NotEmpty String password
) {
}
