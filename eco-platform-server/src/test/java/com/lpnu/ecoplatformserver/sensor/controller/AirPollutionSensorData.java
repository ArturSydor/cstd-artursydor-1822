package com.lpnu.ecoplatformserver.sensor.controller;

import com.lpnu.ecoplatformserver.eco_project.dto.EcoProjectDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionSensorDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static com.lpnu.ecoplatformserver.data.RolesData.INHABITANT;
import static com.lpnu.ecoplatformserver.data.RolesData.MANAGER;

final class AirPollutionSensorData {


    static final UserLoginDto LOGIN_REQUEST_FOR_INHABITANT = new UserLoginDto(
            AIR_POLLUTION_SENSOR_TESTS_STEP + INHABITANT_USER_EMAIL,
            INHABITANT_USER_PASSWORD
    );

    static final UserLoginDto LOGIN_REQUEST_FOR_MANAGER = new UserLoginDto(
            AIR_POLLUTION_SENSOR_TESTS_STEP + MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD
    );

    static final UserDto NEW_MANAGER_USER_DTO = new UserDto(
            null,
            FIRST_NAME,
            LAST_NAME,
            AIR_POLLUTION_SENSOR_TESTS_STEP + MANAGER_USER_EMAIL,
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
                AIR_POLLUTION_SENSOR_TESTS_STEP + MANAGER_USER_EMAIL,
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
            AIR_POLLUTION_SENSOR_TESTS_STEP + ORGANISATION_NAME,
            AIR_POLLUTION_SENSOR_TESTS_STEP + ORGANISATION_EMAIL,
            Boolean.FALSE,
            Boolean.FALSE,
            null,
            NEW_MANAGER_USER_DTO
    );

    static OrganisationSimpleDto getOrganisationDtoAfterCreation(Long organisationId) {
        return new OrganisationSimpleDto(
                organisationId,
                AIR_POLLUTION_SENSOR_TESTS_STEP + ORGANISATION_NAME,
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
                AIR_POLLUTION_SENSOR_TESTS_STEP + INHABITANT_USER_EMAIL,
                INHABITANT_USER_PASSWORD,
                Boolean.TRUE,
                Boolean.FALSE,
                0,
                null,
                getOrganisationDtoAfterCreation(organisationId),
                INHABITANT
        );
    }

    static AirPollutionSensorDto getSensor(Long organisationId, Long managerId) {
        return new AirPollutionSensorDto(
                1L,
                AIR_POLLUTION_SENSOR_IDENTIFIER,
                AIR_POLLUTION_SENSOR_LATITUDE,
                AIR_POLLUTION_SENSOR_LONGITUDE,
                getOrganisationDtoAfterCreation(organisationId),
                getManagerUserDtoAfterCreation(managerId)
                );
    }

    static AirPollutionSensorDto getSensorForUpdate(Long organisationId, Long managerId) {
        return new AirPollutionSensorDto(
                1L,
                AIR_POLLUTION_SENSOR_IDENTIFIER,
                AIR_POLLUTION_SENSOR_LATITUDE_FOR_UPDATE,
                AIR_POLLUTION_SENSOR_LONGITUDE,
                getOrganisationDtoAfterCreation(organisationId),
                getManagerUserDtoAfterCreation(managerId)
        );
    }

}
