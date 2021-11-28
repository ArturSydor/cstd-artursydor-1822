package com.lpnu.ecoplatformserver.organisation.controller;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static com.lpnu.ecoplatformserver.data.RolesData.MANAGER;

final class OrganisationData {

    static final UserLoginDto LOGIN_REQUEST_FOR_MANAGER = new UserLoginDto(
            ORGANISATION_TESTS_STEP + MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD
    );

    static final UserDto NEW_USER_DTO = new UserDto(
            null,
            FIRST_NAME,
            LAST_NAME,
            ORGANISATION_TESTS_STEP + MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD,
            Boolean.TRUE,
            Boolean.FALSE,
            null,
            null,
            MANAGER
    );

    static final OrganisationDto NEW_ORGANISATION_DTO = new OrganisationDto(
            null,
            ORGANISATION_TESTS_STEP + ORGANISATION_NAME,
            ORGANISATION_TESTS_STEP + ORGANISATION_EMAIL,
            Boolean.TRUE,
            Boolean.FALSE,
            null,
            NEW_USER_DTO
    );

    static OrganisationSimpleDto getExpectedOrganisationDtoAfterCreation(Long organisationId) {
        return new OrganisationSimpleDto(
                organisationId,
                ORGANISATION_TESTS_STEP + ORGANISATION_NAME,
                Boolean.TRUE,
                Boolean.FALSE,
                null
        );
    }

    static OrganisationSimpleDto getExpectedOrganisationDtoAfterUpdate(Long organisationId) {
        return new OrganisationSimpleDto(
                organisationId,
                ORGANISATION_TESTS_STEP + ORGANISATION_NAME_FOR_UPDATE,
                Boolean.TRUE,
                Boolean.FALSE,
                null
        );
    }

    static UserDto getCreatedUserDto(Long userId, Long organisationId) {
        return new UserDto(
                userId,
                FIRST_NAME,
                LAST_NAME,
                ORGANISATION_TESTS_STEP + MANAGER_USER_EMAIL,
                null,
                Boolean.TRUE,
                Boolean.FALSE,
                null,
                getExpectedOrganisationDtoAfterUpdate(organisationId),
                MANAGER
        );
    }

    static OrganisationDto getUpdateOrganisationDto(Long organisationId, Long userId) {
        return new OrganisationDto(
                organisationId,
                ORGANISATION_TESTS_STEP + ORGANISATION_NAME_FOR_UPDATE,
                ORGANISATION_TESTS_STEP + ORGANISATION_EMAIL_FOR_UPDATE,
                Boolean.TRUE,
                Boolean.FALSE,
                null,
                getCreatedUserDto(userId, organisationId)
        );
    }

}
