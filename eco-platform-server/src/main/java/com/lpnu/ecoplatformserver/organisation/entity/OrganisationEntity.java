package com.lpnu.ecoplatformserver.organisation.entity;

import com.lpnu.ecoplatformserver.user.entity.UserEntity;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name = "organisation")
@Where(clause = "deleted = false")
public class OrganisationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String email;

    private boolean memberApprovalRequired;

    private boolean deleted;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

}
