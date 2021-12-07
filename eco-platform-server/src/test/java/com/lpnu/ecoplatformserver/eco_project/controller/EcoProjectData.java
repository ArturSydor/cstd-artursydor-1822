package com.lpnu.ecoplatformserver.eco_project.controller;

import com.lpnu.ecoplatformserver.eco_project.dto.EcoProjectDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static com.lpnu.ecoplatformserver.data.RolesData.INHABITANT;
import static com.lpnu.ecoplatformserver.data.RolesData.MANAGER;

final class EcoProjectData {


    static final UserLoginDto LOGIN_REQUEST_FOR_INHABITANT = new UserLoginDto(
            ECO_PROJECT_TESTS_STEP + INHABITANT_USER_EMAIL,
            INHABITANT_USER_PASSWORD
    );

    static final UserLoginDto LOGIN_REQUEST_FOR_MANAGER = new UserLoginDto(
            ECO_PROJECT_TESTS_STEP + MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD
    );


    static final UserDto NEW_MANAGER_USER_DTO = new UserDto(
            null,
            FIRST_NAME,
            LAST_NAME,
            ECO_PROJECT_TESTS_STEP + MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD,
            Boolean.TRUE,
            Boolean.FALSE,
            0,
            null,
            null,
            MANAGER
    );

    static UserDto getManagerUserDtoAfterCreation(Long userId) {
        return new UserDto(
                userId,
                FIRST_NAME,
                LAST_NAME,
                ECO_PROJECT_TESTS_STEP + MANAGER_USER_EMAIL,
                MANAGER_USER_PASSWORD,
                Boolean.TRUE,
                Boolean.FALSE,
                0,
                null,
                null,
                MANAGER
        );
    }

    static final OrganisationDto NEW_ORGANISATION_DTO = new OrganisationDto(
            null,
            ECO_PROJECT_TESTS_STEP + ORGANISATION_NAME,
            ECO_PROJECT_TESTS_STEP + ORGANISATION_EMAIL,
            Boolean.FALSE,
            Boolean.FALSE,
            null,
            NEW_MANAGER_USER_DTO
    );

    static OrganisationSimpleDto getOrganisationDtoAfterCreation(Long organisationId) {
        return new OrganisationSimpleDto(
                organisationId,
                ECO_PROJECT_TESTS_STEP + ORGANISATION_NAME,
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
                ECO_PROJECT_TESTS_STEP + INHABITANT_USER_EMAIL,
                INHABITANT_USER_PASSWORD,
                Boolean.TRUE,
                Boolean.FALSE,
                0,
                null,
                getOrganisationDtoAfterCreation(organisationId),
                INHABITANT
        );
    }

    static EcoProjectDto getEcoProject(Long organisationId, Long managerId) {
        return new EcoProjectDto(
                1L,
                ECO_PROJECT_NAME,
                ECO_PROJECT_DESCRIPTION,
                0,
                ECO_PROJECT_MAX_ALLOWED_POINTS,
                Boolean.TRUE,
                Boolean.FALSE,
                null,
                getManagerUserDtoAfterCreation(managerId),
                getOrganisationDtoAfterCreation(organisationId)
        );
    }

    static EcoProjectDto getEcoProjectForClose(Long organisationId, Long managerId) {
        return new EcoProjectDto(
                1L,
                ECO_PROJECT_NAME,
                ECO_PROJECT_DESCRIPTION,
                3,
                ECO_PROJECT_MAX_ALLOWED_POINTS,
                Boolean.TRUE,
                Boolean.TRUE,
                null,
                getManagerUserDtoAfterCreation(managerId),
                getOrganisationDtoAfterCreation(organisationId)
        );
    }

}
