package com.lpnu.ecoplatformserver.user.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String name;

}
