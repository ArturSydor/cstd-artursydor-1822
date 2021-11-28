package com.lpnu.ecoplatformserver.eco_project.entity;

import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "eco_projects")
public class EcoProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    private int points;

    private int maxAllowedPointsPerUser;

    private boolean published;

    private boolean closed;

    private LocalDateTime created = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private OrganisationEntity organisation;

    public String briefInfo() {
        return String.format("""
                Name: %s,
                Published: %s,
                Closed: %s
                """, name, published, closed);
    }

}
