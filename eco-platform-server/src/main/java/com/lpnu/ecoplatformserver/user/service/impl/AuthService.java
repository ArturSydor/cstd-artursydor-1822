package com.lpnu.ecoplatformserver.user.service.impl;

import com.lpnu.ecoplatformserver.exception.DuplicatedEmailException;
import com.lpnu.ecoplatformserver.exception.NoLoggedInUserException;
import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import com.lpnu.ecoplatformserver.security.JwtProvider;
import com.lpnu.ecoplatformserver.user.dto.AuthenticationResponseDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import com.lpnu.ecoplatformserver.user.mapper.IUserMapper;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import com.lpnu.ecoplatformserver.user.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private final IUserMapper userMapper;

    @Override
    public Long register(UserDto registrationDto) {
        Objects.requireNonNull(registrationDto);
        checkIfUserNotExist(registrationDto.email());
        UserEntity userEntity = userMapper.mapToEntity(registrationDto);
        userEntity.setPassword(encryptPassword(userEntity.getPassword()));
        return userRepository.save(userEntity).getId();
    }

    @Override
    public UserEntity registerFirstOrganisationUser(UserDto userDto, OrganisationEntity organisationEntity) {
        Objects.requireNonNull(userDto);
        Objects.requireNonNull(organisationEntity);
        checkIfUserNotExist(userDto.email());
        UserEntity user = userMapper.mapToEntity(userDto);
        user.setOrganisation(organisationEntity);
        user.setPassword(encryptPassword(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AuthenticationResponseDto login(UserLoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthenticationResponseDto(jwtProvider.generateToken(authentication), loginDto.email());
    }

    @Override
    public Optional<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new NoLoggedInUserException("No logged in user");
        }
        String email = ((org.springframework.security.core.userdetails.User)
                authentication.getPrincipal()).getUsername();
        return userRepository.findByEmail(email);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkIfUserNotExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicatedEmailException("Email [%s] already exists", email);
        }
    }
}
