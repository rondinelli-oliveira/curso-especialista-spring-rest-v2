package com.evolution.food.api.infrastructure.repository.spec;

import com.evolution.food.api.domain.model.Restaurant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantWithFreeFreightSpec implements Specification<Restaurant> {

    @Override
    public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return builder.equal(root.get("freightRate"), BigDecimal.ZERO);
    }
}
