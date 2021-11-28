package com.lpnu.ecoplatformserver.eco_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@IdClass(UserProjectPointsId.class)
@Table(name = "user_eco_project_points")
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectPointsEntity {

    @Id
    private Long userId;

    @Id
    private Long projectId;

    private int points;

}
