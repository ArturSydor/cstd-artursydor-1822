package com.lpnu.ecoplatformserver.organisation.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record OrganisationSimpleDto(
        Long id,
        @NotEmpty String name,
        boolean memberApprovalRequired,
        boolean deleted) {
}
