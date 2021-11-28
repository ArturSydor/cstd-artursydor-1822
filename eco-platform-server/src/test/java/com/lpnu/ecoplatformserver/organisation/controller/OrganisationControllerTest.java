package com.lpnu.ecoplatformserver.organisation.controller;

import com.lpnu.ecoplatformserver.AbstractTest;
import com.lpnu.ecoplatformserver.TestUtilities;
import com.lpnu.ecoplatformserver.exception.EntityNotFoundException;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.Set;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.ORGANISATION_URL;
import static com.lpnu.ecoplatformserver.organisation.controller.OrganisationData.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class OrganisationControllerTest extends AbstractTest {

    private Long organisationId;
    private Long creatorId;

    private String url;

    @BeforeAll
    void init() {
        url = String.format(ORGANISATION_URL, port);
    }

    @Test
    @Order(1)
    void create() {
        var response = testRestTemplate.postForEntity(url, NEW_ORGANISATION_DTO, OrganisationDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        organisationId = response.getBody().id();
        creatorId = response.getBody().creator().id();
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
        var actualOrganisation = response.getBody().stream().filter(o -> Objects.equals(o.id(), organisationId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong. No organisation found with id=" + organisationId));
        assertEquals(getExpectedOrganisationDtoAfterCreation(organisationId), actualOrganisation);
    }

    @Test
    @Order(3)
    void updateForbidden() {
        var response = testRestTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(getUpdateOrganisationDto(organisationId, creatorId)), OrganisationDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(4)
    void updateOk() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(getUpdateOrganisationDto(organisationId, creatorId), header), OrganisationDto.class);

            assertNotNull(response);
            assertNotNull(response.getBody());
            log.info(response.getBody().toString());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(getUpdateOrganisationDto(organisationId, creatorId), response.getBody());
        });
    }

    @Test
    @Order(5)
    void getOne() {
        var response = testRestTemplate.getForEntity(url + "/" + organisationId, OrganisationDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(getUpdateOrganisationDto(organisationId, creatorId), response.getBody());
    }

    @Test
    @Order(6)
    void deleteForbidden() {
        var response = testRestTemplate.exchange(url + "/" + organisationId, HttpMethod.DELETE, null, Void.class);

        assertNotNull(response);
        log.info(response.toString());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(7)
    void delete() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(url + "/" + organisationId, HttpMethod.DELETE, new HttpEntity<>(header), Void.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        });
    }

    @Test
    @Order(8)
    void getOneForDeleted() {
        var response = testRestTemplate.getForEntity(url + "/" + organisationId, String.class);

        assertNotNull(response);
        log.info(response.toString());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}