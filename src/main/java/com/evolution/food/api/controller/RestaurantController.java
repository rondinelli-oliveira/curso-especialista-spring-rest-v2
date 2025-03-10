package com.evolution.food.api.controller;

import com.evolution.food.api.core.validation.ValidateException;
import com.evolution.food.api.domain.exception.BusinessException;
import com.evolution.food.api.domain.exception.KitchenNotFoundException;
import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.RestaurantRepository;
import com.evolution.food.api.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
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
    
    private final SmartValidator smartValidator;

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantService restaurantService,
                                SmartValidator smartValidator) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
        this.smartValidator = smartValidator;
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
    public Restaurant add(@RequestBody @Valid Restaurant restaurant) {
        try {
            return restaurantService.save(restaurant);
        } catch (KitchenNotFoundException exception) {
            throw new BusinessException(exception.getMessage());
        }

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
                             @RequestBody @Valid Restaurant restaurant) {
        Restaurant currentRestaurant = restaurantService.searchOrFail(id);

        BeanUtils.copyProperties(restaurant, currentRestaurant,
                "id", "paymentMethods", "address", "registrationDate", "products");

        try {
            return restaurantService.save(currentRestaurant);
        } catch (KitchenNotFoundException exception) {
            throw new BusinessException(exception.getMessage());
        }
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
    public Restaurant partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        Restaurant currentRestaurant = restaurantRepository.findById(id).orElse(null);

        merge(fields, currentRestaurant, request);
        validate(currentRestaurant, "restaurant");

        return update(id, currentRestaurant);
    }

    private void validate(Restaurant currentRestaurant, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(currentRestaurant, objectName);

        smartValidator.validate(currentRestaurant, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidateException(bindingResult);
        }
    }

    private static void merge(Map<String, Object> originFields, Restaurant destinationFields, HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurant originRestaurant = objectMapper.convertValue(originFields, Restaurant.class);

            originFields.forEach((attributeName, attributeValue)-> {

                Field field = ReflectionUtils.findField(Restaurant.class, attributeName);
                field.setAccessible(true);

                Object newValue = ReflectionUtils.getField(field, originRestaurant);

                ReflectionUtils.setField(field, destinationFields, newValue);
            });
        }catch (IllegalArgumentException ex){
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        restaurantService.remove(id);
    }
}
