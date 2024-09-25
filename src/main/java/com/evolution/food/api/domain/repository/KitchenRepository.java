package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

//	List<Kitchen> findByName(String name);

}