package com.evolution.food.api.domain.repository;

import com.evolution.food.api.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepositoryV2 extends CustomJpaRepository<Restaurant, Long>, /*RestaurantRepositoryQueries,*/
        JpaSpecificationExecutor<Restaurant> {

    List<Restaurant> findByFreightRateBetween(BigDecimal initialFreightRate, BigDecimal finalFreightRate);

//    @Query("from Restaurant where name like %:name% and kitchen.id = :id")
    List<Restaurant> findForName(String name, @Param("id") Long kitchen);

    List<Restaurant> findByNameContainingAndKitchenId(String name, Long id);

    Optional<Restaurant> findFirstRestaurantByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countByKitchenId(Long id);

}