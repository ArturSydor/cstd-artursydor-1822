package com.lpnu.ecoplatformserver.organisation.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record OrganisationSimpleDto(
        Long id,
        @NotEmpty @Length(min = 1) String name,
        boolean memberApprovalRequired,
        boolean deleted,
        LocalDateTime created) {
}
