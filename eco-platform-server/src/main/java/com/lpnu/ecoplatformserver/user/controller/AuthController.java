package com.lpnu.ecoplatformserver.user.controller;

import com.lpnu.ecoplatformserver.user.dto.AuthenticationResponseDto;
import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;
import com.lpnu.ecoplatformserver.user.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponseDto login(@RequestBody @Valid UserLoginDto loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto registrationRequest) {
        return ResponseEntity.ok(authService.register(registrationRequest));
    }
}