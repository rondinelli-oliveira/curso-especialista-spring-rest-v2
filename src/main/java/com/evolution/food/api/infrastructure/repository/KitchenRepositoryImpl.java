package com.evolution.food.api.infrastructure.repository;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.repository.KitchenRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KitchenRepositoryImpl implements KitchenRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Kitchen> findAll() {
		return manager.createQuery("from Kitchen", Kitchen.class)
				.getResultList();
	}

	@Override
	public Kitchen findById(Long id) {
		return null;
	}

	@Override
	public Kitchen save(Kitchen kitchen) {
		return null;
	}

	@Override
	public void remove(Kitchen kitchen) {

	}
}