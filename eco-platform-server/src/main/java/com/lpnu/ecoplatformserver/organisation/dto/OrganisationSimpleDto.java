package com.lpnu.ecoplatformserver.organisation.dto;

import javax.validation.constraints.NotNull;

public record OrganisationSimpleDto(
        Long id,
        @NotNull String name,
        boolean memberApprovalRequired,
        boolean deleted) {
}
