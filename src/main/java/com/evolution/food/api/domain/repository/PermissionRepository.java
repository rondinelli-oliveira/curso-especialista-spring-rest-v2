package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.Permission;

import java.util.List;

public interface PermissionRepository {

	List<Permission> findAll();
	Permission findById(Long id);
	Permission save(Permission permission);
	void remove(Permission permission);
	
}