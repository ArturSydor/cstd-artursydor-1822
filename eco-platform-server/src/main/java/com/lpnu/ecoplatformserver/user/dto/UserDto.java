package com.lpnu.ecoplatformserver.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record UserDto(
        Long id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull @Email String email
) {
}
