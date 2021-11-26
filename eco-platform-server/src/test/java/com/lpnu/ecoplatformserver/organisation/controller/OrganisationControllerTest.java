package com.lpnu.ecoplatformserver.organisation.controller;

import com.lpnu.ecoplatformserver.AbstractTest;
import com.lpnu.ecoplatformserver.TestUtilities;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static com.lpnu.ecoplatformserver.data.AuthData.LOGIN_REQUEST_FOR_MANAGER;
import static com.lpnu.ecoplatformserver.data.CommonTestConstants.ORGANISATION_ID;
import static com.lpnu.ecoplatformserver.data.CommonTestConstants.ORGANISATION_URL;
import static com.lpnu.ecoplatformserver.data.OrganisationData.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class OrganisationControllerTest extends AbstractTest {

    private String url;

    @BeforeAll
    void init() {
        url = String.format(ORGANISATION_URL, port);
    }

    @Test
    @Order(1)
    void create() {
        var response = testRestTemplate.postForEntity(url, NEW_ORGANISATION_DTO, String.class);

        assertNotNull(response);
        log.info(response.toString());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(2)
    void getAllPreview() {
        var response = TestUtilities.getParametrizedResponse(testRestTemplate, url, new ParameterizedTypeReference<Set<OrganisationSimpleDto>>() {
        });

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertFalse(response.getBody().isEmpty());
        var actualOrganisation = response.getBody().iterator().next();
        assertEquals(EXPECTED_ORGANISATION_DTO_AFTER_CREATION, actualOrganisation);
    }

    @Test
    @Order(3)
    void updateForbidden() {
        var response = testRestTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(UPDATE_ORGANISATION_DTO), OrganisationDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(4)
    void updateOk() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(UPDATE_ORGANISATION_DTO, header), OrganisationDto.class);

            assertNotNull(response);
            assertNotNull(response.getBody());
            log.info(response.getBody().toString());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(UPDATE_ORGANISATION_DTO, response.getBody());
        });
    }

    @Test
    @Order(5)
    void getOne() {
        var response = testRestTemplate.getForEntity(url + "/" + ORGANISATION_ID, OrganisationDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UPDATE_ORGANISATION_DTO, response.getBody());
    }

    @Test
    @Order(6)
    void deleteForbidden() {
        var response = testRestTemplate.exchange(url + "/" + ORGANISATION_ID, HttpMethod.DELETE, null, Void.class);

        assertNotNull(response);
        log.info(response.toString());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(7)
    void delete() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(url + "/" + ORGANISATION_ID, HttpMethod.DELETE, new HttpEntity<>(header), Void.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        });
    }

    @Test
    @Order(8)
    void getOneForDeleted() {
        var response = testRestTemplate.getForEntity(url + "/" + ORGANISATION_ID, String.class);

        assertNotNull(response);
        log.info(response.toString());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}