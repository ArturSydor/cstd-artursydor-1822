package com.lpnu.ecoplatformserver.user.controller;

import com.lpnu.ecoplatformserver.user.dto.UserDto;
import com.lpnu.ecoplatformserver.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/approvals")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsersJoinRequests(@RequestParam @NotNull Long organisationId) {
        return userService.getAllUsersJoinRequests(organisationId);
    }

    @PostMapping("/approvals/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveUser(@PathVariable Long userId) {
        userService.activateUser(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto getByEmail(@RequestParam String email) {
        return userService.getOneByEmail(email);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
    }

}
