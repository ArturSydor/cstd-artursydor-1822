package com.lpnu.ecoplatformserver.user.controller;

import com.lpnu.ecoplatformserver.user.dto.RoleDto;
import com.lpnu.ecoplatformserver.user.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

}
