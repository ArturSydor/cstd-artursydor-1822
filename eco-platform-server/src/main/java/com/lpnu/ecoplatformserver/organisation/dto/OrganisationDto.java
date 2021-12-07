package com.lpnu.ecoplatformserver.organisation.dto;

import com.lpnu.ecoplatformserver.user.dto.UserDto;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record OrganisationDto(
        Long id,
        @NotEmpty @Length(min = 1) String name,
        @NotEmpty @Email String email,
        boolean memberApprovalRequired,
        boolean deleted,
        LocalDateTime created,
        @Valid UserDto creator) {
}
