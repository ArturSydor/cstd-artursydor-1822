package com.lpnu.ecoplatformserver.sensor.controller;

import com.lpnu.ecoplatformserver.AbstractTest;
import com.lpnu.ecoplatformserver.TestUtilities;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionCalculationResultDto;
import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionSensorDto;
import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionDataEntity;
import com.lpnu.ecoplatformserver.sensor.entity.AirPollutionSensorEntity;
import com.lpnu.ecoplatformserver.sensor.repository.AirPollutionDataRepository;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class AirPollutionSensorControllerTest extends AbstractTest {

    @Autowired
    private AirPollutionDataRepository airPollutionDataRepository;

    private Long organisationId;
    private Long organisationCreatorUserId;

    @BeforeAll
    void init() {
        setUpOrganisation();
    }

    @Test
    @Order(1)
    void create() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, AirPollutionSensorData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.postForEntity(String.format(AIR_POLLUTION_SENSOR_URL, port),
                    new HttpEntity<>(AirPollutionSensorData.getSensor(organisationId, organisationCreatorUserId), header), String.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.OK, response.getStatusCode());
        });
    }

    @Test
    @Order(2)
    void getAllSensors() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, AirPollutionSensorData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = TestUtilities.getParametrizedResponseSecured(testRestTemplate, header,
                    String.format(AIR_POLLUTION_SENSOR_URL, port), new ParameterizedTypeReference<List<AirPollutionSensorDto>>() {
            });

            assertNotNull(response);
            assertNotNull(response.getBody());
            log.info(response.toString());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            int expectedNumberOfSensors = 1;
            assertEquals(expectedNumberOfSensors, response.getBody().size());
        });
    }

    @Test
    @Order(3)
    void update() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, AirPollutionSensorData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(String.format(AIR_POLLUTION_SENSOR_URL, port), HttpMethod.PUT,
                    new HttpEntity<>(AirPollutionSensorData.getSensorForUpdate(organisationId, organisationCreatorUserId), header), AirPollutionSensorDto.class);

            assertNotNull(response);
            assertNotNull(response.getBody());
            log.info(response.toString());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(AIR_POLLUTION_SENSOR_LATITUDE_FOR_UPDATE, response.getBody().latitude());
        });
    }

    @Test
    @Order(4)
    void getOne() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, AirPollutionSensorData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(String.format(AIR_POLLUTION_SENSOR_URL + "/%s", port, 1), HttpMethod.GET,
                    new HttpEntity<>(header), AirPollutionSensorDto.class);

            assertNotNull(response);
            assertNotNull(response.getBody());
            log.info(response.toString());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(AIR_POLLUTION_SENSOR_LATITUDE_FOR_UPDATE, response.getBody().latitude());
        });
    }

    @Test
    @Order(5)
    void getSensorWithLowestAirQualityFailsWithoutData() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, AirPollutionSensorData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(String.format(AIR_POLLUTION_SENSOR_URL + "/report", port), HttpMethod.GET,
                    new HttpEntity<>(header), String.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        });
    }

    @Test
    @Order(6)
    void getSensorWithLowestAirQualityOk() {
        var airPollutionData = new AirPollutionDataEntity();
        var sensor = new AirPollutionSensorEntity();
        sensor.setId(1L);
        airPollutionData.setSensor(sensor);
        airPollutionDataRepository.saveAndFlush(airPollutionData);

        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, AirPollutionSensorData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(String.format(AIR_POLLUTION_SENSOR_URL + "/report", port), HttpMethod.GET,
                    new HttpEntity<>(header), AirPollutionCalculationResultDto.class);

            assertNotNull(response);
            assertNotNull(response.getBody());
            log.info(response.toString());

            assertEquals(HttpStatus.OK, response.getStatusCode());
        });
        airPollutionDataRepository.deleteAll();
    }

    @Test
    void delete() {
        TestUtilities.performAuthenticated(() -> TestUtilities.login(testRestTemplate, port, AirPollutionSensorData.LOGIN_REQUEST_FOR_MANAGER), header -> {
            var response = testRestTemplate.exchange(String.format(AIR_POLLUTION_SENSOR_URL + "/%s", port, 1), HttpMethod.DELETE,
                    new HttpEntity<>(header), Void.class);

            assertNotNull(response);
            log.info(response.toString());

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        });
    }

    private void setUpOrganisation() {
        var organisationUrl = String.format(ORGANISATION_URL, port);
        var response = testRestTemplate.postForEntity(organisationUrl, AirPollutionSensorData.NEW_ORGANISATION_DTO, OrganisationDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        organisationId = response.getBody().id();
        organisationCreatorUserId = response.getBody().creator().id();
    }
}