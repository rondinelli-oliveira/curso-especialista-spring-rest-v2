package com.evolution.food.api.domain.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Kitchen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Kitchen kitchen = (Kitchen) obj;
        return Objects.equals(id, kitchen.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
