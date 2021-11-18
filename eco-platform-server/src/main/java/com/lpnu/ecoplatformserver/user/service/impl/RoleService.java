package com.lpnu.ecoplatformserver.user.service.impl;

import com.lpnu.ecoplatformserver.user.dto.RoleDto;
import com.lpnu.ecoplatformserver.user.mapper.IRoleMapper;
import com.lpnu.ecoplatformserver.user.repository.RoleRepository;
import com.lpnu.ecoplatformserver.user.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    private final IRoleMapper roleMapper;

    @Override
    public List<RoleDto> getAll() {
        return roleRepository
                .findAll()
                .stream()
                .map(roleMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
