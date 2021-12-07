package com.lpnu.ecoplatformserver.user.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public record ChangePasswordRequestDto(
        @NotEmpty @Email String email,
        @NotEmpty @Length(min = 1) String newPassword
) {
}
