package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {

    List<Restaurant> find(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate);

    List<Restaurant> dinamicFind(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate);
}
