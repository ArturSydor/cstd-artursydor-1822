package com.lpnu.ecoplatformserver.user.controller;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static com.lpnu.ecoplatformserver.data.CommonTestConstants.ORGANISATION_EMAIL;
import static com.lpnu.ecoplatformserver.data.RolesData.INHABITANT;
import static com.lpnu.ecoplatformserver.data.RolesData.MANAGER;

final class AuthUserData {

    static final UserDto NEW_MANAGER_USER_DTO = new UserDto(
            null,
            FIRST_NAME,
            LAST_NAME,
            AUTH_TESTS_STEP + MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD,
            Boolean.TRUE,
            Boolean.FALSE,
            null,
            null,
            MANAGER
    );

    static final OrganisationDto NEW_ORGANISATION_DTO = new OrganisationDto(
            null,
            AUTH_TESTS_STEP + ORGANISATION_NAME,
            AUTH_TESTS_STEP + ORGANISATION_EMAIL,
            Boolean.FALSE,
            Boolean.FALSE,
            null,
            NEW_MANAGER_USER_DTO
    );

    static OrganisationSimpleDto getOrganisationDtoAfterCreation(Long organisationId) {
        return new OrganisationSimpleDto(
                organisationId,
                AUTH_TESTS_STEP + ORGANISATION_NAME,
                Boolean.FALSE,
                Boolean.FALSE,
                null
        );
    }

    static UserDto getNewUsualUserDto(Long organisationId) {
        return new UserDto(
                null,
                FIRST_NAME,
                LAST_NAME,
                AUTH_TESTS_STEP + INHABITANT_USER_EMAIL,
                INHABITANT_USER_PASSWORD,
                Boolean.TRUE,
                Boolean.FALSE,
                null,
                getOrganisationDtoAfterCreation(organisationId),
                INHABITANT
        );
    }

    static final UserLoginDto LOGIN_REQUEST_FOR_INHABITANT = new UserLoginDto(
            AUTH_TESTS_STEP + INHABITANT_USER_EMAIL,
            INHABITANT_USER_PASSWORD
    );

    static final UserLoginDto LOGIN_REQUEST_FOR_INHABITANT_WITH_WRONG_PASSWORD = new UserLoginDto(
            AUTH_TESTS_STEP + INHABITANT_USER_EMAIL,
            AUTH_TESTS_STEP + INHABITANT_USER_PASSWORD
    );


}
