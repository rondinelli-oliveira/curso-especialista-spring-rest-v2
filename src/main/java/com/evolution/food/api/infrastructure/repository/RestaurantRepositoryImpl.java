package com.evolution.food.api.infrastructure.repository;

import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.RestaurantRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurant> find(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate) {
        var jpql = "from Restaurant where name like :name " +
                "and freightRate between :initialFreightRate and :finalFreightRate";

        return manager.createQuery(jpql, Restaurant.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("initialFreightRate", initialFreightRate )
                .setParameter("finalFreightRate", finalFreightRate)
                .getResultList();
    }

}
