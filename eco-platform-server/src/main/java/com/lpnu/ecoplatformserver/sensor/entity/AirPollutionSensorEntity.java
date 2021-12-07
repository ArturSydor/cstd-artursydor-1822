package com.lpnu.ecoplatformserver.sensor.entity;

import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "air_pollution_sensors")
@Entity
public class AirPollutionSensorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String externalIdentifier;

    private String latitude;

    private String longitude;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private OrganisationEntity organisation;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;
}
