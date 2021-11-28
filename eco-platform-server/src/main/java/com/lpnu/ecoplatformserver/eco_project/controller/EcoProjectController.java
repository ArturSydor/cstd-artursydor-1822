package com.lpnu.ecoplatformserver.eco_project.controller;

import com.lpnu.ecoplatformserver.eco_project.dto.EcoProjectDto;
import com.lpnu.ecoplatformserver.eco_project.service.IEcoProjectService;
import com.lpnu.ecoplatformserver.eco_project.service.IProjectPointsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eco-projects")
@RequiredArgsConstructor
public class EcoProjectController {

    private final IEcoProjectService ecoProjectService;
    private final IProjectPointsService projectPointsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EcoProjectDto> getAll(@RequestParam boolean personalOnly) {
        return ecoProjectService.getAll(personalOnly);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EcoProjectDto getOne(@PathVariable Long id) {
        return ecoProjectService.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody EcoProjectDto ecoProjectDto) {
        ecoProjectService.create(ecoProjectDto);
    }

    @PostMapping("/{id}/points")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProjectPoints(@PathVariable Long id, @RequestParam int points) {
        projectPointsService.updatePoints(id, points);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EcoProjectDto update(@RequestBody EcoProjectDto ecoProjectDto) {
        return ecoProjectService.update(ecoProjectDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ecoProjectService.delete(id);
    }

}
