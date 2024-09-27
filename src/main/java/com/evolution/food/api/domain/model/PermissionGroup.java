package com.evolution.food.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class PermissionGroup {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "permission_group_permissions", joinColumns = @JoinColumn(name = "permission_group_id"),
            inverseJoinColumns = @JoinColumn(name = "permissions_id"))
    private List<Permission> permissions= new ArrayList<>();
}
