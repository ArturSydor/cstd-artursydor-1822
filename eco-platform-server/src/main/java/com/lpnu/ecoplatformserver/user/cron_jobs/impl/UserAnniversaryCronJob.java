package com.lpnu.ecoplatformserver.user.cron_jobs.impl;

import com.lpnu.ecoplatformserver.user.cron_jobs.ICronJob;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import com.lpnu.ecoplatformserver.user.repository.UserRepository;
import com.lpnu.ecoplatformserver.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;


/**
 * Every day @UserAnniversaryCronJob checks how long user presents in system
 * and base on this data assign additional points each year.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserAnniversaryCronJob implements ICronJob {

    private static final int LAST_FEBRUARY_DAY_IN_LEAP_YEAR = 50;

    private static final int NUMBER_OF_DAYS_IN_NOT_LEAP_YEAR = 365;

    private final UserRepository userRepository;

    private final IUserService userService;

    @Async
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void execute() {
        long start = System.currentTimeMillis();
        log.debug("UserAnniversaryCronJob started");

        var todayDate = LocalDateTime.now();

        userRepository.findAllByActiveIsTrue()
                .stream()
                .filter(user -> hasJoinedThisDay(user, todayDate))
                .forEach(user -> userService.updatePoints(user, 1));

        log.debug("UserAnniversaryCronJob finished after {} milliseconds", System.currentTimeMillis() - start);
    }

    private boolean hasJoinedThisDay(UserEntity user, LocalDateTime todayDate) {
        if (isLeapYear(user.getJoined().getYear()) && isLeapYear(todayDate.getYear())) {
            return user.getJoined().getDayOfYear() == todayDate.getDayOfYear();
        } else if (isLeapYear(user.getJoined().getYear()) && !isLeapYear(todayDate.getYear())) {
            return onlyFirstIsLeapYear(user.getJoined(), todayDate);
        } else if (!isLeapYear(user.getJoined().getYear()) && isLeapYear(todayDate.getYear())) {
            return onlyFirstIsLeapYear(todayDate, user.getJoined());
        } else {
            return user.getJoined().getDayOfYear() == todayDate.getDayOfYear();
        }
    }

    private boolean onlyFirstIsLeapYear(LocalDateTime first, LocalDateTime second) {
        var firstDayOfYear = first.getDayOfYear();
        if(firstDayOfYear >= LAST_FEBRUARY_DAY_IN_LEAP_YEAR) {
            return --firstDayOfYear == second.getDayOfYear();
        }

        return firstDayOfYear == second.getDayOfYear();
    }

    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > NUMBER_OF_DAYS_IN_NOT_LEAP_YEAR;
    }

}
