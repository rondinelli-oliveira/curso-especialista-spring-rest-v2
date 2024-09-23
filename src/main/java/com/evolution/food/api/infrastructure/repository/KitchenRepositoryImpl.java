package com.evolution.food.api.infrastructure.repository;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.repository.KitchenRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
		return manager.find(Kitchen.class, id);
	}

	@Transactional
	@Override
	public Kitchen save(Kitchen kitchen) {
		return manager.merge(kitchen);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		Kitchen kitchen = findById(id);

		if (null == kitchen) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(kitchen);
	}
}