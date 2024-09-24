package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.RestaurantRepository;
import com.evolution.food.api.domain.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
@Slf4j
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantService restaurantService) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant: restaurants) {
            log.info("O nome do restaurante de codigo: {} e {}", restaurant.getId(), restaurant.getName());
        }
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findById(@PathVariable Long id) {
        Restaurant restaurant = restaurantRepository.findById(id);

        if (null != restaurant) {
            log.info("Pesquisando restaurante com codigo: {}", id);
            log.info("Nome do restaurante  {}", restaurant.getName());
            return ResponseEntity.ok(restaurant);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {
        try {
            restaurantService.save(restaurant);
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        try {
            Restaurant currentRestaurant = restaurantRepository.findById(id);

            if (null != currentRestaurant) {
                log.info("Atualizando restaurante de codigo: {} e nome {}, para {}", currentRestaurant.getId(), currentRestaurant.getName(),
                        restaurant.getName());
                BeanUtils.copyProperties(restaurant, currentRestaurant, "id");
                restaurantService.save(currentRestaurant);
                return ResponseEntity.ok(currentRestaurant);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
            Restaurant currentRestaurant = restaurantRepository.findById(id);

            if (null == currentRestaurant) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        merge(fields, currentRestaurant);

        return update(id, currentRestaurant);
    }

    private static void merge(Map<String, Object> originFefields, Restaurant destinationFields) {
        originFefields.forEach((attributeName, attributeValue)-> {
            log.info(attributeName + " = " + attributeValue);
        });
    }

}
