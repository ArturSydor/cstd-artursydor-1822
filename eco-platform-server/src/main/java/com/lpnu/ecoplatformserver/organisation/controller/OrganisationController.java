package com.lpnu.ecoplatformserver.organisation.controller;

import com.lpnu.ecoplatformserver.organisation.dto.OrganisationDto;
import com.lpnu.ecoplatformserver.organisation.dto.OrganisationSimpleDto;
import com.lpnu.ecoplatformserver.organisation.service.IOrganisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/organisations")
@RequiredArgsConstructor
public class OrganisationController {

    private final IOrganisationService organisationService;

    @GetMapping("/{id}")
    public ResponseEntity<OrganisationDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(organisationService.getOne(id));
    }

    @GetMapping
    public ResponseEntity<Set<OrganisationSimpleDto>> getAllPreview() {
        return ResponseEntity.ok(organisationService.getAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrganisationDto> create(@RequestBody @Valid OrganisationDto organisationDto) {
        return ResponseEntity.ok(organisationService.create(organisationDto));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrganisationDto> update(@RequestBody @Valid OrganisationDto organisationDto) {
        return ResponseEntity.ok(organisationService.update(organisationDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        organisationService.delete(id);
    }

}
