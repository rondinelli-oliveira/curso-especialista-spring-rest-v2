package com.evolution.food.api.infrastructure.repository.spec;

import com.evolution.food.api.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecsFactory {

    public static Specification<Restaurant> withFreeFreight() {

        return (root, query, builder) ->
                builder.equal(root.get("freightRate"), BigDecimal.ZERO);
    }

    public static Specification<Restaurant> withSimilarName(String name) {

        return (root, query, builder) ->
                builder.like(root.get("name"), "%" + name + "%");
    }
}
