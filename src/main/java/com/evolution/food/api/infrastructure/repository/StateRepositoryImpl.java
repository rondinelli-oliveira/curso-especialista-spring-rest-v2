package com.evolution.food.api.infrastructure.repository;

import com.evolution.food.api.domain.model.State;
import com.evolution.food.api.domain.repository.StateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StateRepositoryImpl implements StateRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<State> findAll() {
		return manager.createQuery("from State", State.class)
				.getResultList();
	}

	@Override
	public State findById(Long id) {
		return manager.find(State.class, id);
	}

	@Override
	public State save(State state) {
		return null;
	}

	@Override
	public void remove(State state) {

	}
}