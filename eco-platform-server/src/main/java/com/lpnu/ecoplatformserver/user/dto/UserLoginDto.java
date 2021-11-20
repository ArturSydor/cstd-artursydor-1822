package com.lpnu.ecoplatformserver.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record UserLoginDto(
        @NotNull @NotEmpty String email,
        @NotNull @NotEmpty String password
) {
}
