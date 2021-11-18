package com.lpnu.ecoplatformserver.organisation.service.impl;

import com.lpnu.ecoplatformserver.organisation.repository.OrganisationRepository;
import com.lpnu.ecoplatformserver.organisation.service.IOrganisationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrganisationService implements IOrganisationService {

    private final OrganisationRepository organisationRepository;

}
