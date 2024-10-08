package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.KitchenRepository;
import com.evolution.food.api.domain.repository.KitchenRepositoryV2;
import com.evolution.food.api.domain.repository.RestaurantRepository;
import com.evolution.food.api.domain.repository.RestaurantRepositoryV2;
import com.evolution.food.api.infrastructure.repository.spec.RestaurantWithFreeFreightSpec;
import com.evolution.food.api.infrastructure.repository.spec.RestauranteWithSimilarNameSpec;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.evolution.food.api.infrastructure.repository.spec.RestaurantSpecsFactory.*;

@RestController
@RequestMapping("/teste")
public class TesteController {

    private final KitchenRepository kitchenRepository;

    private final RestaurantRepository restaurantRepository;

    private final RestaurantRepositoryV2 restaurantRepositoryV2;

    private final KitchenRepositoryV2 kitchenRepositoryV2;

    public TesteController(KitchenRepository kitchenRepository, RestaurantRepository restaurantRepository, RestaurantRepositoryV2 restaurantRepositoryV2, KitchenRepositoryV2 kitchenRepositoryV2) {
        this.kitchenRepository = kitchenRepository;
        this.restaurantRepository = restaurantRepository;
        this.restaurantRepositoryV2 = restaurantRepositoryV2;
        this.kitchenRepositoryV2 = kitchenRepositoryV2;
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

    @GetMapping("/kitchens/find-first-kitchen")
    public Optional<Kitchen> findFirstKitchan() {
        return kitchenRepositoryV2.findFirst();
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

    @GetMapping("/restaurantes/for-name-and-freight-rate")
    public List<Restaurant> find(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate) {
        return restaurantRepository.find(name, initialFreightRate, finalFreightRate);
    }

    @GetMapping("/restaurantes/dinamic-find")
    public List<Restaurant> dinamicFind(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate) {
        return restaurantRepository.dinamicFind(name, initialFreightRate, finalFreightRate);
    }

    @GetMapping("/restaurantes/find-with-criteria")
    public List<Restaurant> findWithCriteria(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate) {
        return restaurantRepository.findWithCriteria(name, initialFreightRate, finalFreightRate);
    }

    @GetMapping("/restaurantes/find-with-dinamic-criteria")
    public List<Restaurant> findWithDinamicCriteria(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate) {
        return restaurantRepository.findWithDinamicCriteria(name, initialFreightRate, finalFreightRate);
    }

    @GetMapping("/restaurantes/find-with-free-freight")
    public List<Restaurant> findWithFreeFreight(String name) {

        var withFreeFreight = new RestaurantWithFreeFreightSpec();
        var withSimilarName = new RestauranteWithSimilarNameSpec(name);

        return restaurantRepository.findAll(withFreeFreight.and(withSimilarName));
    }

    @GetMapping("/restaurantes/find-with-free-freight-with-specs")
    public List<Restaurant> findWithFreeFreightSpecs(String name) {
        return restaurantRepository.findAll(withFreeFreight().and(withSimilarName(name)));
    }

    @GetMapping("/restaurantes/find-with-free-freight-v2")
    public List<Restaurant> findWithFreeFreightV2(String name) {
        return restaurantRepository.findWithFreeFreight(name);
    }

    @GetMapping("/restaurantes/find-first-restaurant")
    public Optional<Restaurant> findFirstRestaurant() {
        return restaurantRepositoryV2.findFirst();
    }
}
