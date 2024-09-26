package com.evolution.food.api.infrastructure.repository.spec;

import com.evolution.food.api.domain.model.Restaurant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class RestauranteWithSimilarNameSpec implements Specification<Restaurant> {

	private String name;
	
	@Override
	public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query,
								 CriteriaBuilder builder) {
		
		return builder.like(root.get("name"), "%" + name + "%");
	}

}