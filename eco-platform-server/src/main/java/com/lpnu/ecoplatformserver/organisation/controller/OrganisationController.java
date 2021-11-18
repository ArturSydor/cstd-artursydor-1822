package com.lpnu.ecoplatformserver.organisation.controller;

import com.lpnu.ecoplatformserver.organisation.service.IOrganisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organisations")
@RequiredArgsConstructor
public class OrganisationController {

    private final IOrganisationService organisationService;

}
