package com.lpnu.ecoplatformserver.eco_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectPointsId implements Serializable {
    private Long userId;
    private Long projectId;
}
