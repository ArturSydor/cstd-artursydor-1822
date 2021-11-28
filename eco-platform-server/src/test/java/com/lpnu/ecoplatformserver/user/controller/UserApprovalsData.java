package com.lpnu.ecoplatformserver.user.controller;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static com.lpnu.ecoplatformserver.data.RolesData.INHABITANT;
import static com.lpnu.ecoplatformserver.data.RolesData.MANAGER;

final class UserApprovalsData {

    static final UserLoginDto LOGIN_REQUEST_FOR_INHABITANT = new UserLoginDto(
            USER_TESTS_STEP + INHABITANT_USER_EMAIL,
            INHABITANT_USER_PASSWORD
    );

    static final UserLoginDto LOGIN_REQUEST_FOR_MANAGER = new UserLoginDto(
            USER_TESTS_STEP + MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD
    );

    static final UserDto NEW_MANAGER_USER_DTO = new UserDto(
            null,
            FIRST_NAME,
            LAST_NAME,
            USER_TESTS_STEP + MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD,
            Boolean.TRUE,
            Boolean.FALSE,
            null,
            MANAGER
    );

    static final OrganisationDto NEW_ORGANISATION_DTO = new OrganisationDto(
            null,
            USER_TESTS_STEP + ORGANISATION_NAME,
            USER_TESTS_STEP + ORGANISATION_EMAIL,
            Boolean.TRUE,
            Boolean.FALSE,
            NEW_MANAGER_USER_DTO
    );

    static OrganisationSimpleDto getOrganisationDtoAfterCreation(Long organisationId) {
        return new OrganisationSimpleDto(
                organisationId,
                USER_TESTS_STEP + ORGANISATION_NAME,
                Boolean.TRUE,
                Boolean.FALSE
        );
    }

    static UserDto getNewUsualUserDto(Long organisationId) {
        return new UserDto(
                null,
                FIRST_NAME,
                LAST_NAME,
                USER_TESTS_STEP + INHABITANT_USER_EMAIL,
                INHABITANT_USER_PASSWORD,
                Boolean.TRUE,
                Boolean.FALSE,
                getOrganisationDtoAfterCreation(organisationId),
                INHABITANT
        );
    }

    static UserDto getRegisteredUsualUserDto(Long userId, Long organisationId) {
        return new UserDto(
                userId,
                FIRST_NAME,
                LAST_NAME,
                USER_TESTS_STEP + INHABITANT_USER_EMAIL,
                null,
                Boolean.FALSE,
                Boolean.FALSE,
                getOrganisationDtoAfterCreation(organisationId),
                INHABITANT
        );
    }

}
