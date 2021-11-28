package com.lpnu.ecoplatformserver.user.controller;

import com.lpnu.ecoplatformserver.AbstractTest;
import com.lpnu.ecoplatformserver.TestUtilities;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.user.dto.AuthenticationResponseDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static com.lpnu.ecoplatformserver.data.RolesData.INHABITANT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class AuthControllerTest extends AbstractTest {

    private Long organisationId;

    @BeforeAll
    void init() {
        var organisationUrl = String.format(ORGANISATION_URL, port);
        var response = testRestTemplate.postForEntity(organisationUrl, AuthUserData.NEW_ORGANISATION_DTO, OrganisationDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        organisationId = response.getBody().id();
    }

    @Test
    @Order(1)
    void register() {
        var registrationUrl = String.format(AUTH_REGISTER_URL, port);
        var response = testRestTemplate.postForEntity(registrationUrl, AuthUserData.getNewUsualUserDto(organisationId), UserDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    void registerTheSameUserTwiceShouldFail() {
        var registrationUrl = String.format(AUTH_REGISTER_URL, port);
        var response = testRestTemplate.postForEntity(registrationUrl, AuthUserData.getNewUsualUserDto(organisationId), String.class);

        assertNotNull(response);
        log.info(response.toString());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @Order(3)
    void login() {
        var loginUrl = String.format(AUTH_LOGIN_URL, port);
        var response = testRestTemplate.postForEntity(loginUrl, AuthUserData.LOGIN_REQUEST_FOR_INHABITANT, AuthenticationResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AuthUserData.LOGIN_REQUEST_FOR_INHABITANT.email(), response.getBody().email());
        assertEquals(organisationId, response.getBody().organisationId());
        assertEquals(INHABITANT.name(), response.getBody().role());
    }

    @Test
    @Order(4)
    void loginWithBadCredentialsFails() {
        var loginUrl = String.format(AUTH_LOGIN_URL, port);
        var response = testRestTemplate.postForEntity(loginUrl, AuthUserData.LOGIN_REQUEST_FOR_INHABITANT_WITH_WRONG_PASSWORD, String.class);

        assertNotNull(response);
        log.info(response.toString());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}