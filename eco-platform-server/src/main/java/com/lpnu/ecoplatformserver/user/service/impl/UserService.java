package com.lpnu.ecoplatformserver.user.service.impl;

import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import com.lpnu.ecoplatformserver.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        Objects.requireNonNull(user);
        return userRepository.save(user);
    }
}
