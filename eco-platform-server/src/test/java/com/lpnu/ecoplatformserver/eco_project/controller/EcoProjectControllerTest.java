package com.lpnu.ecoplatformserver.eco_project.controller;

import com.lpnu.ecoplatformserver.AbstractTest;
import com.lpnu.ecoplatformserver.TestUtilities;
import com.lpnu.ecoplatformserver.eco_project.dto.EcoProjectDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import com.lpnu.ecoplatformserver.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class EcoProjectControllerTest extends AbstractTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    private Long organisationId;
    private Long organisationCreatorUserId;
    private Long registeredUserId;

    @BeforeAll
    void init() {
        setUpOrganisation();
        registerUsualUser();
    }

    @Test
    @Order(1)
    void create() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, EcoProjectData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.postForEntity(String.format(ECO_PROJECTS_URL, port), new HttpEntity<>(EcoProjectData.getEcoProject(organisationId, organisationCreatorUserId), header), Void.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        });
    }

    @Test
    @Order(2)
    void updateProjectPointsFirstTimeOk() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, EcoProjectData.LOGIN_REQUEST_FOR_INHABITANT), header -> {
            var response = testRestTemplate.postForEntity(String.format(ECO_PROJECTS_URL + "/%s/points?points=%s", port, 1, 1), new HttpEntity<>(header), Void.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        });
    }

    @Test
    @Order(3)
    void updateProjectPointsWithoutAvailablePointsFails() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, EcoProjectData.LOGIN_REQUEST_FOR_INHABITANT), header -> {
            var response = testRestTemplate.postForEntity(String.format(ECO_PROJECTS_URL + "/%s/points?points=%s", port, 1, 1), new HttpEntity<>(header), Void.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        });
    }

    @Test
    @Order(4)
    void updateProjectPointsUsingAdditionalAvailablePointsOk() {
        updateAvailableUserPoints(2);
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, EcoProjectData.LOGIN_REQUEST_FOR_INHABITANT), header -> {
            var response = testRestTemplate.postForEntity(String.format(ECO_PROJECTS_URL + "/%s/points?points=%s", port, 1, 2), new HttpEntity<>(header), Void.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        });
    }


    @Test
    @Order(5)
    void updateProjectPointsReachedMaxPointsPerUser() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, EcoProjectData.LOGIN_REQUEST_FOR_INHABITANT), header -> {
            var response = testRestTemplate.postForEntity(String.format(ECO_PROJECTS_URL + "/%s/points?points=%s", port, 1, 1), new HttpEntity<>(header), Void.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        });
    }

    @Test
    @Order(6)
    void closeProject() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, EcoProjectData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(String.format(ECO_PROJECTS_URL, port), HttpMethod.PUT, new HttpEntity<>(EcoProjectData.getEcoProjectForClose(organisationId, organisationCreatorUserId), header), EcoProjectDto.class);

            assertNotNull(response);
            assertNotNull(response.getBody());
            log.info(response.toString());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().closed());
        });
    }

    @Test
    @Order(7)
    void updateProjectPointsIsNotAllowedForClosedProject() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, EcoProjectData.LOGIN_REQUEST_FOR_INHABITANT), header -> {
            var response = testRestTemplate.postForEntity(String.format(ECO_PROJECTS_URL + "/%s/points?points=%s", port, 1, 1), new HttpEntity<>(header), Void.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        });
    }

    private void setUpOrganisation() {
        var organisationUrl = String.format(ORGANISATION_URL, port);
        var response = testRestTemplate.postForEntity(organisationUrl, EcoProjectData.NEW_ORGANISATION_DTO, OrganisationDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        organisationId = response.getBody().id();
        organisationCreatorUserId = response.getBody().creator().id();
    }

    private void registerUsualUser() {
        var registrationUrl = String.format(AUTH_REGISTER_URL, port);
        var response = testRestTemplate.postForEntity(registrationUrl, EcoProjectData.getNewUsualUserDto(organisationId), UserDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        registeredUserId = response.getBody().id();
    }

    private void updateAvailableUserPoints(int points) {
        var user = userService.findOne(registeredUserId);

        user.setAvailablePoints(points);

        userRepository.saveAndFlush(user);
    }

}