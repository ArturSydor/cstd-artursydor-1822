package com.lpnu.ecoplatformserver.user.controller;

import com.lpnu.ecoplatformserver.AbstractTest;
import com.lpnu.ecoplatformserver.TestUtilities;
import com.lpnu.ecoplatformserver.user.dto.RoleDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.ROLES_URL;
import static com.lpnu.ecoplatformserver.data.RolesData.INHABITANT;
import static com.lpnu.ecoplatformserver.data.RolesData.MANAGER;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class RoleControllerTest extends AbstractTest {

    private String url;

    @BeforeAll
    void init() {
        url = String.format(ROLES_URL, port);
    }

    @Test
    void getAll() {

        var response = TestUtilities.getParametrizedResponse(testRestTemplate, url, new ParameterizedTypeReference<List<RoleDto>>() {
        });

        assertNotNull(response);
        assertNotNull(response.getBody());
        log.info(response.getBody().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertFalse(response.getBody().isEmpty());
        var expectedRoles = List.of(MANAGER, INHABITANT);
        var actualRoles = response.getBody();
        assertIterableEquals(expectedRoles, actualRoles);
    }

}