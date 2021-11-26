package com.lpnu.ecoplatformserver.data;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static com.lpnu.ecoplatformserver.data.RolesData.MANAGER;

public final class OrganisationData {

    public static final UserDto NEW_USER_DTO = new UserDto(
            MANAGER_ID,
            FIRST_NAME,
            LAST_NAME,
            MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD,
            Boolean.TRUE,
            Boolean.FALSE,
            null,
            MANAGER
    );

    public static final OrganisationDto NEW_ORGANISATION_DTO = new OrganisationDto(
            ORGANISATION_ID,
            ORGANISATION_NAME,
            ORGANISATION_EMAIL,
            Boolean.TRUE,
            Boolean.FALSE,
            NEW_USER_DTO
    );

    public static final OrganisationSimpleDto EXPECTED_ORGANISATION_DTO_AFTER_CREATION = new OrganisationSimpleDto(
            ORGANISATION_ID,
            ORGANISATION_NAME,
            Boolean.TRUE,
            Boolean.FALSE
    );

    public static final OrganisationSimpleDto EXPECTED_ORGANISATION_DTO_AFTER_UPDATE = new OrganisationSimpleDto(
            ORGANISATION_ID,
            ORGANISATION_NAME_FOR_UPDATE,
            Boolean.TRUE,
            Boolean.FALSE
    );

    public static final UserDto CREATED_USER_DTO = new UserDto(
            MANAGER_ID,
            FIRST_NAME,
            LAST_NAME,
            MANAGER_USER_EMAIL,
            null,
            Boolean.TRUE,
            Boolean.FALSE,
            EXPECTED_ORGANISATION_DTO_AFTER_UPDATE,
            MANAGER
    );

    public static final OrganisationDto UPDATE_ORGANISATION_DTO = new OrganisationDto(
            ORGANISATION_ID,
            ORGANISATION_NAME_FOR_UPDATE,
            ORGANISATION_EMAIL_FOR_UPDATE,
            Boolean.TRUE,
            Boolean.FALSE,
            CREATED_USER_DTO
    );

}
