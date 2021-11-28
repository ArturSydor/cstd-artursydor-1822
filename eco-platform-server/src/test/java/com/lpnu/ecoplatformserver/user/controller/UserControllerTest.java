package com.lpnu.ecoplatformserver.user.controller;

import com.lpnu.ecoplatformserver.AbstractTest;
import com.lpnu.ecoplatformserver.TestUtilities;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.user.dto.AuthenticationResponseDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static com.lpnu.ecoplatformserver.data.RolesData.INHABITANT;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UserControllerTest extends AbstractTest {

    private Long organisationId;
    private Long registeredUserId;

    @BeforeAll
    void init() {
        setUpOrganisation();
        registerUsualUser();
    }

    @Test
    @Order(1)
    void getAllUsersJoinRequests() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, UserApprovalsData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = TestUtilities.getParametrizedResponseSecured(testRestTemplate, header,
                    String.format(USER_APPROVALS_URL, port) + "?organisationId=" + organisationId, new ParameterizedTypeReference<List<UserDto>>() {
                    });

            assertNotNull(response);
            assertNotNull(response.getBody());
            log.info(response.getBody().toString());

            assertEquals(HttpStatus.OK, response.getStatusCode());

            assertFalse(response.getBody().isEmpty());
            var actualUser = response.getBody().stream().filter(u -> Objects.equals(u.id(), registeredUserId)).findFirst();
            assertTrue(actualUser.isPresent());
            assertEquals(UserApprovalsData.getRegisteredUsualUserDto(registeredUserId, organisationId), actualUser.get());
        });
    }

    @Test
    @Order(2)
    void loginFailsWithNonActiveUser() {
        var loginUrl = String.format(AUTH_LOGIN_URL, port);
        var response = testRestTemplate.postForEntity(loginUrl, UserApprovalsData.LOGIN_REQUEST_FOR_INHABITANT, String.class);

        assertNotNull(response);
        log.info(response.toString());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @Order(3)
    void approveUser() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, UserApprovalsData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.postForEntity(String.format(USER_APPROVALS_URL, port) + "/" + registeredUserId, new HttpEntity<>(header), UserDto.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        });
    }

    @Test
    @Order(4)
    void loginOkWithActiveUser() {
        var loginUrl = String.format(AUTH_LOGIN_URL, port);
        var response = testRestTemplate.postForEntity(loginUrl, UserApprovalsData.LOGIN_REQUEST_FOR_INHABITANT, AuthenticationResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserApprovalsData.LOGIN_REQUEST_FOR_INHABITANT.email(), response.getBody().email());
        assertEquals(organisationId, response.getBody().organisationId());
        assertEquals(INHABITANT.name(), response.getBody().role());
    }

    private void setUpOrganisation() {
        var organisationUrl = String.format(ORGANISATION_URL, port);
        var response = testRestTemplate.postForEntity(organisationUrl, UserApprovalsData.NEW_ORGANISATION_DTO, OrganisationDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        organisationId = response.getBody().id();
    }

    private void registerUsualUser() {
        var registrationUrl = String.format(AUTH_REGISTER_URL, port);
        var response = testRestTemplate.postForEntity(registrationUrl, UserApprovalsData.getNewUsualUserDto(organisationId), UserDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        registeredUserId = response.getBody().id();
    }

}