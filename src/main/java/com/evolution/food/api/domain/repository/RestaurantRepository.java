package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository  extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByFreightRateBetween(BigDecimal initialFreightRate, BigDecimal finalFreightRate);

    List<Restaurant> findByNameContainingAndKitchenId(String name, Long id);

}