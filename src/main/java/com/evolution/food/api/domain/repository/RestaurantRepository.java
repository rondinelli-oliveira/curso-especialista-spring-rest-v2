package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

	List<Restaurant> findAll();
	Restaurant findById(Long id);
	Restaurant save(Restaurant restaurant);
	void remove(Restaurant restaurant);
	
}