package com.lpnu.ecoplatformserver.user.service.impl;

import com.lpnu.ecoplatformserver.exception.EntityNotFoundException;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import com.lpnu.ecoplatformserver.user.mapper.IUserMapper;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import com.lpnu.ecoplatformserver.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final IUserMapper userMapper;

    @Override
    public List<UserDto> getAllUsersJoinRequests(Long organisationId) {
        return userRepository.findAllByOrganisation_IdAndActiveFalse(organisationId)
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity findOne(Long id) {
        Objects.requireNonNull(id);
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id=%s doesn't exists", id));
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        Objects.requireNonNull(user);
        return userRepository.save(user);
    }

    @Override
    public void activateUser(Long id) {
        UserEntity user = findOne(id);
        user.setActive(Boolean.TRUE);
        userRepository.save(user);
    }

}
