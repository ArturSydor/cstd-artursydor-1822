package com.lpnu.ecoplatformserver.user.entity;

import com.lpnu.ecoplatformserver.organisation.entity.OrganisationEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private boolean active = Boolean.TRUE;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private OrganisationEntity organisation;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

}
