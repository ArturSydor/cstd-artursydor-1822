package com.lpnu.ecoplatformserver.organisation.entity;

import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "organisation")
public class OrganisationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String email;

    private boolean isMemberApprovalRequired;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

}
