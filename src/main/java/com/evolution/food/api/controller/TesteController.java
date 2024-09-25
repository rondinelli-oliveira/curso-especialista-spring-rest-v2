package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.KitchenRepository;
import com.evolution.food.api.domain.repository.RestaurantRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    private final KitchenRepository kitchenRepository;

    private final RestaurantRepository restaurantRepository;

    public TesteController(KitchenRepository kitchenRepository, RestaurantRepository restaurantRepository) {
        this.kitchenRepository = kitchenRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/kitchens/all-by-name")
    public List<Kitchen> kitchensByName(@RequestParam("name") String name) {
        return kitchenRepository.findAllByName(name);
    }

    @GetMapping("/kitchens/all-by-name-like")
    public List<Kitchen> kitchensByNameContaining(@RequestParam("name") String name) {
        return kitchenRepository.findAllByNameContaining(name);
    }

    @GetMapping("/kitchens/by-name")
    public Optional<Kitchen> findByName(@RequestParam("name") String name) {
        return kitchenRepository.findByName(name);
    }

    @GetMapping("/kitchens/exists")
    public boolean existsByName(String name) {
        return kitchenRepository.existsByName(name);
    }

    @GetMapping("/restaurantes/by-freight-rate")
    public List<Restaurant> findByFreightRateBetween(BigDecimal initialFreightRate, BigDecimal finalFreightRate) {
        return restaurantRepository.findByFreightRateBetween(initialFreightRate, finalFreightRate);
    }

    @GetMapping("/restaurantes/for-name")
    public List<Restaurant> findForName(String name, Long id) {
        return restaurantRepository.findForName(name, id);
    }

    @GetMapping("/restaurantes/by-name")
    public List<Restaurant> findByNameContainingAndKitchenId(String name, Long id) {
        return restaurantRepository.findByNameContainingAndKitchenId(name, id);
    }

    @GetMapping("/restaurantes/first-for-name")
    public Optional<Restaurant> findFirstRestaurantByNameContaining(String name) {
        return restaurantRepository.findFirstRestaurantByNameContaining(name);
    }

    @GetMapping("/restaurantes/top2-for-name")
    public List<Restaurant> findTop2ByNameContaining(String name) {
        return restaurantRepository.findTop2ByNameContaining(name);
    }

    @GetMapping("/restaurantes/count-for-kitchen")
    public int countByKitchenId(Long id) {
        return restaurantRepository.countByKitchenId(id);
    }
}
