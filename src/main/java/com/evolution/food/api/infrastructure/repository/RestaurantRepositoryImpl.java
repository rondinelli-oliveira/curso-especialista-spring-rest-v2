package com.evolution.food.api.infrastructure.repository;

import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.RestaurantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurant> findAll() {
		return manager.createQuery("from Restaurant", Restaurant.class)
				.getResultList();
	}

	@Override
	public Restaurant findById(Long id) {
		return manager.find(Restaurant.class, id);
	}

	@Override
	public Restaurant save(Restaurant restaurant) {
		return null;
	}

	@Override
	public void remove(Restaurant restaurant) {

	}
}