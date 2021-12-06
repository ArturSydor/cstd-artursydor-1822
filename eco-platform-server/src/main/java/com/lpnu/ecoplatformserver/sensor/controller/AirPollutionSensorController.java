package com.lpnu.ecoplatformserver.sensor.controller;

import com.lpnu.ecoplatformserver.sensor.dto.AirPollutionSensorDto;
import com.lpnu.ecoplatformserver.sensor.service.IAirPollutionSensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/air-pollution-sensor")
@RequiredArgsConstructor
public class AirPollutionSensorController {

    private final IAirPollutionSensorService airPollutionSensorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AirPollutionSensorDto> getAllSensors() {
        return airPollutionSensorService.getAllSensors();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public AirPollutionSensorDto getOne(@PathVariable Long id) {
        return airPollutionSensorService.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AirPollutionSensorDto create(@RequestBody @Valid AirPollutionSensorDto newSensor) {
        return airPollutionSensorService.create(newSensor);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AirPollutionSensorDto update(@RequestBody @Valid AirPollutionSensorDto sensor) {
        return airPollutionSensorService.update(sensor);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        airPollutionSensorService.delete(id);
    }

}
