package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.State;

import java.util.List;

public interface StateRepository {

	List<State> findAll();
	State findById(Long id);
	State save(State state);
	void remove(Long id);
	
}