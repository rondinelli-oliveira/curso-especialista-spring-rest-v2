package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.KitchenRepository;
import com.evolution.food.api.domain.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final KitchenRepository kitchenRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, KitchenRepository kitchenRepository) {
        this.restaurantRepository = restaurantRepository;
        this.kitchenRepository = kitchenRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        log.info("Persistindo restaurante de nome: {}", restaurant.getName());
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRepository.findById(kitchenId)
                .orElseThrow(() -> new EntityNotFoundException(
                    String.format("Nao existe cadastro de cozinha com codigo: %d ", kitchenId)));

        restaurant.setKitchen(kitchen);
        return restaurantRepository.save(restaurant);
    }
}
