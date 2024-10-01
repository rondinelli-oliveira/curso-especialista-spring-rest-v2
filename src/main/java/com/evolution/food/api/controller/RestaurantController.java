package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.RestaurantRepository;
import com.evolution.food.api.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
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
        return restaurants;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Restaurant> findById(@PathVariable Long id) {
//        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
//
//        if (restaurant.isPresent()) {
//            log.info("Pesquisando restaurante com codigo: {}", id);
//            log.info("Nome do restaurante  {}", restaurant.get().getName());
//            return ResponseEntity.ok(restaurant.get());
//        }
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/{id}")
    public Restaurant findById(@PathVariable Long id) {
        return restaurantService.searchOrFail(id);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {
//        try {
//            restaurantService.save(restaurant);
//            return  ResponseEntity.status(HttpStatus.CREATED)
//                    .body(restaurant);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.badRequest()
//                    .body(e.getMessage());
//        }
//
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant add(@RequestBody Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
//        try {
//            Restaurant currentRestaurant = restaurantRepository.findById(id).orElse(null);
//
//            if (null != currentRestaurant) {
//                log.info("Atualizando restaurante de codigo: {} e nome {}, para {}", currentRestaurant.getId(), currentRestaurant.getName(),
//                        restaurant.getName());
//                BeanUtils.copyProperties(restaurant, currentRestaurant,
//                        "id", "paymentMethods", "address", "registrationDate", "products");
//                restaurantService.save(currentRestaurant);
//                return ResponseEntity.ok(currentRestaurant);
//            }
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.badRequest()
//                    .body(e.getMessage());
//        }
//    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id,
                             @RequestBody Restaurant restaurant) {
        Restaurant currentRestaurant = restaurantService.searchOrFail(id);

        BeanUtils.copyProperties(restaurant, currentRestaurant,
                "id", "paymentMethods", "address", "registrationDate", "products");

        return restaurantService.save(currentRestaurant);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
//            Restaurant currentRestaurant = restaurantRepository.findById(id).orElse(null);
//
//            if (null == currentRestaurant) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//
//        merge(fields, currentRestaurant);
//
//        return update(id, currentRestaurant);
//    }

    @PatchMapping("/{id}")
    public Restaurant partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        Restaurant currentRestaurant = restaurantRepository.findById(id).orElse(null);

        merge(fields, currentRestaurant);

        return update(id, currentRestaurant);
    }

    private static void merge(Map<String, Object> originFields, Restaurant destinationFields) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant originRestaurant = objectMapper.convertValue(originFields, Restaurant.class);

        originFields.forEach((attributeName, attributeValue)-> {

            Field field = ReflectionUtils.findField(Restaurant.class, attributeName);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, originRestaurant);

            ReflectionUtils.setField(field, destinationFields, newValue);
        });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        restaurantService.remove(id);
    }
}
