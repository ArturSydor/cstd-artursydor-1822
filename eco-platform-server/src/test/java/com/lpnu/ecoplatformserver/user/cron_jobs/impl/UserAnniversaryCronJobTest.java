package com.lpnu.ecoplatformserver.user.cron_jobs.impl;

import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import com.lpnu.ecoplatformserver.user.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAnniversaryCronJobTest {

    @Mock
    private IUserService mockedUserService;

    @Mock
    private UserRepository mockedUserRepository;

    @InjectMocks
    private UserAnniversaryCronJob userAnniversaryCronJob;

    @Test
    void executeForEmptyUsersTable() {
        when(mockedUserRepository.findAllByActiveIsTrue()).thenReturn(Collections.emptyList());
        userAnniversaryCronJob.execute();
        verify(mockedUserRepository, times(1)).findAllByActiveIsTrue();
        verify(mockedUserService, never()).updatePoints(any(), anyInt());
    }

    @Test
    void executeForUserWithAnniversary() {
        var user = getUser(LocalDateTime.now());
        when(mockedUserRepository.findAllByActiveIsTrue()).thenReturn(Collections.singletonList(user));
        userAnniversaryCronJob.execute();
        verify(mockedUserRepository, times(1)).findAllByActiveIsTrue();
        verify(mockedUserService, times(1)).updatePoints(user, 1);
    }

    @Test
    void executeForUserWithoutAnniversary() {
        when(mockedUserRepository.findAllByActiveIsTrue()).thenReturn(Collections.singletonList(getUser(LocalDateTime.now().minusMonths(1))));
        userAnniversaryCronJob.execute();
        verify(mockedUserRepository, times(1)).findAllByActiveIsTrue();
        verify(mockedUserService, never()).updatePoints(any(), anyInt());
    }

    private UserEntity getUser(LocalDateTime joined) {
        var user = new UserEntity();
        user.setId(1L);
        user.setJoined(joined);
        return user;
    }
}