package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.City;

import java.util.List;

public interface CityRepository {

	List<City> findAll();
	City findById(Long id);
	City save(City city);
	void remove(City city);
	
}