package com.lpnu.ecoplatformserver.user.service.impl;

import com.lpnu.ecoplatformserver.exception.NoActiveUserFound;
import com.lpnu.ecoplatformserver.security.OrganisationUser;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=[%s] not found", email)));
        checkIfUserIsActive(user);
        return new OrganisationUser(user.getId(), user.getOrganisation().getId(), user.getEmail(),
                user.getPassword(), true, true, true,
                true, getAuthorities(user.getRole().getName()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String... role) {
        return Arrays.stream(role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private void checkIfUserIsActive(UserEntity user) {
        if (!user.isActive()) {
            throw new NoActiveUserFound("User %s is not active!", user.getEmail());
        }
    }
}
