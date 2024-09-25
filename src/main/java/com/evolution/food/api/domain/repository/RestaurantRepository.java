package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository  extends JpaRepository<Restaurant, Long> {

}