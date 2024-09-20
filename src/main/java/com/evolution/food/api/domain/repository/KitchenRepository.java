package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.Kitchen;

import java.util.List;

public interface KitchenRepository {

	List<Kitchen> findAll();
	Kitchen findById(Long id);
	Kitchen save(Kitchen kitchen);
	void remove(Kitchen kitchen);
	
}