package com.lpnu.ecoplatformserver.user.service.impl;

import com.lpnu.ecoplatformserver.exception.DuplicatedEntryException;
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
    public UserEntity findOneByEmail(String email) {
        Objects.requireNonNull(email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email=%s doesn't exists", email));
    }

    @Override
    public UserDto getOneByEmail(String email) {
        return userMapper.mapToDto(findOneByEmail(email));
    }

    @Override
    public void updateUser(UserDto user) {
        Objects.requireNonNull(user);

        var existingUser = findOne(user.id());

        checkIfEmailDoesNotExist(user, existingUser);

        existingUser.setFirstName(user.firstName());
        existingUser.setLastName(user.lastName());
        existingUser.setEmail(user.email());
        existingUser.setDeleted(user.deleted());
        existingUser.setActive(user.active());

        userRepository.save(existingUser);
    }

    @Override
    public void activateUser(Long id) {
        UserEntity user = findOne(id);
        user.setActive(Boolean.TRUE);
        userRepository.save(user);
    }

    @Override
    public void updatePoints(UserEntity user, int points) {
        user.setAvailablePoints(user.getAvailablePoints() + points);
        userRepository.save(user);
    }

    private void checkIfEmailDoesNotExist(UserDto dto, UserEntity entity) {
        if (!Objects.equals(dto.email(), entity.getEmail())) {
            userRepository.findByEmail(dto.email())
                    .ifPresent(user -> {
                        throw new DuplicatedEntryException("Email [%s] already exists", user.getEmail());
                    });
        }
    }

}
