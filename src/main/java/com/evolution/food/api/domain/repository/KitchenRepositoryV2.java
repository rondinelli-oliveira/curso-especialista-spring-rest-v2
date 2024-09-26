package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.Kitchen;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KitchenRepositoryV2 extends CustomJpaRepository<Kitchen, Long> {

	List<Kitchen> findAllByName(String name);

	List<Kitchen> findAllByNameContaining(String name);

    Optional<Kitchen> findByName(String name);

	boolean existsByName(String name);

}