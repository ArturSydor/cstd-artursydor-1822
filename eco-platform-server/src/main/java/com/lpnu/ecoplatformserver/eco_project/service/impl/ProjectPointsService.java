package com.lpnu.ecoplatformserver.eco_project.service.impl;

import com.lpnu.ecoplatformserver.eco_project.entity.EcoProjectEntity;
import com.lpnu.ecoplatformserver.eco_project.entity.UserProjectPointsEntity;
import com.lpnu.ecoplatformserver.eco_project.entity.UserProjectPointsId;
import com.lpnu.ecoplatformserver.eco_project.repository.UserProjectPointsRepository;
import com.lpnu.ecoplatformserver.eco_project.service.IEcoProjectService;
import com.lpnu.ecoplatformserver.eco_project.service.IProjectPointsService;
import com.lpnu.ecoplatformserver.security.OrganisationUser;
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
public class ProjectPointsService implements IProjectPointsService {

    private final IEcoProjectService ecoProjectService;
    private final IUserService userService;

    private final UserProjectPointsRepository userProjectPointsRepository;

    private final OrganisationUser currentUser;


    @Override
    public void updatePoints(Long projectId, int points) {
        Objects.requireNonNull(projectId);
        var projectEntity = ecoProjectService.findOne(projectId);
        checkIfPointsUpdateAllowed(projectEntity, points);

        userProjectPointsRepository.findById(new UserProjectPointsId(currentUser.getUserId(), projectId))
                .ifPresentOrElse(pointsEntity -> updatePointsEntity(pointsEntity, projectEntity.getMaxAllowedPointsPerUser(), points),
                        () -> saveNewPointsEntity(projectId, points));

        projectEntity.setPoints(projectEntity.getPoints() + points);
        ecoProjectService.save(projectEntity);
    }

    private void saveNewPointsEntity(Long projectId, int points) {
        checkIfUserHasEnoughPoints(points, Boolean.TRUE);
        userProjectPointsRepository.save(new UserProjectPointsEntity(currentUser.getUserId(), projectId, points));
    }

    private void updatePointsEntity(UserProjectPointsEntity pointsEntity, int maxAllowedPointPerUser, int points) {
        int newPointsValue = pointsEntity.getPoints() + points;
        if (newPointsValue > maxAllowedPointPerUser) {
            throw new IllegalStateException(String.format("It is not allowed to update more than %s points for one user for this project", maxAllowedPointPerUser));
        }
        checkIfUserHasEnoughPoints(points, Boolean.FALSE);
        pointsEntity.setPoints(newPointsValue);
        userProjectPointsRepository.save(pointsEntity);
    }

    private void checkIfPointsUpdateAllowed(EcoProjectEntity entity, int points) {
        if (Objects.equals(entity.getCreator().getId(), currentUser.getUserId())) {
            throw new IllegalStateException("Creator cannot update his own project!");
        }
        if (!entity.isPublished() || entity.isClosed()) {
            throw new IllegalStateException("Points update is not allows for project\n" + entity.briefInfo());
        }
        if (entity.getMaxAllowedPointsPerUser() < points) {
            throw new IllegalStateException(String.format("It is not allowed to update more than %s points for one user for this project", entity.getMaxAllowedPointsPerUser()));
        }
    }

    private int getCurrentUserPoints() {
        return userService.findOne(currentUser.getUserId()).getAvailablePoints();
    }

    private void checkIfUserHasEnoughPoints(int points, boolean isFirstPoints) {
        int availablePoints = getCurrentUserPoints();
        if (((isFirstPoints && points > 1) && availablePoints < points - 1)
                || (!isFirstPoints && availablePoints < points)) {
            log.error("User {} has available only {} and it is not enough to support project with {} points", currentUser.getUsername(), availablePoints, points);
            throw new IllegalStateException("User don't have enough points to support this project!");
        }
    }

}
