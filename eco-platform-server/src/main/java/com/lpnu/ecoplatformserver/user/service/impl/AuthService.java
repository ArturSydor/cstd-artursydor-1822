package com.lpnu.ecoplatformserver.user.service.impl;

import com.lpnu.ecoplatformserver.exception.DuplicatedEntryException;
import com.lpnu.ecoplatformserver.exception.ObjectAccessDeniedException;
import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import com.lpnu.ecoplatformserver.security.JwtProvider;
import com.lpnu.ecoplatformserver.security.OrganisationUser;
import com.lpnu.ecoplatformserver.user.dto.AuthenticationResponseDto;
import com.lpnu.ecoplatformserver.user.dto.ChangePasswordRequestDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import com.lpnu.ecoplatformserver.user.mapper.IUserMapper;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import com.lpnu.ecoplatformserver.user.service.IAuthService;
import com.lpnu.ecoplatformserver.user.service.IUserService;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private final IUserMapper userMapper;

    private final OrganisationUser currentUser;

    @Override
    public Long register(UserDto registrationDto) {
        Objects.requireNonNull(registrationDto);
        checkIfUserNotExist(registrationDto.email());
        UserEntity userEntity = userMapper.mapToEntity(registrationDto);
        userEntity.setPassword(encryptPassword(userEntity.getPassword()));
        if (userEntity.getOrganisation().isMemberApprovalRequired()) {
            userEntity.setActive(Boolean.FALSE);
        }
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
        user.setActive(Boolean.TRUE);
        return userRepository.save(user);
    }

    @Override
    public AuthenticationResponseDto login(UserLoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthenticationResponseDto(jwtProvider.generateToken(authentication), loginDto.email(),
                authentication.getAuthorities().iterator().next().getAuthority(),
                ((OrganisationUser) authentication.getPrincipal()).getOrganisationId());
    }

    @Override
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        if (Objects.equals(currentUser.getUsername(), changePasswordRequestDto.email())) {
            var user = userService.findOneByEmail(changePasswordRequestDto.email());
            var newPassword = encryptPassword(changePasswordRequestDto.newPassword());
            if (newPassword.equals(user.getPassword())) {
                throw new DuplicatedEntryException("Your new password is the same as old one, please enter another");
            }
            user.setPassword(newPassword);
            userRepository.save(user);
        } else {
            throw new ObjectAccessDeniedException("Password update is not allowed!");
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkIfUserNotExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicatedEntryException("Email [%s] already exists", email);
        }
    }
}
