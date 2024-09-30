package com.evolution.food.api.controller;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import com.evolution.food.api.domain.model.City;
import com.evolution.food.api.domain.repository.CityRepository;
import com.evolution.food.api.domain.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
@Slf4j
public class CityController {

    private final CityRepository cityRepository;

    private final CityService cityService;

    public CityController(CityRepository cityRepository, CityService cityService) {
        this.cityRepository = cityRepository;
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> findAll() {
        List<City> cities = cityRepository.findAll();
        for (City city: cities) {
            log.info("O nome da cidade de codigo: {} e {}", city.getId(), city.getName());
        }
        return cities;
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id) {
        Optional<City> city = cityRepository.findById(id);

        if (city.isPresent()) {
            log.info("Pesquisando cidade pelo codigo: {}", id);
            log.info("Nome da cidade {}", city.get().getName());
            return ResponseEntity.ok(city.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody City city) {
        try {
            city = cityService.save(city);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(city);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                       @RequestBody City city) {
        try {
            City currentCity = cityRepository.findById(id).orElse(null);

            if (currentCity != null) {
                log.info("Atualizando cidade de codigo: {} e nome {}, para {}", currentCity.getId(), currentCity.getName(),
                        city.getName());
                BeanUtils.copyProperties(city, currentCity, "id");

                currentCity = cityService.save(currentCity);
                return ResponseEntity.ok(currentCity);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<City> remove(@PathVariable Long id) {
        try {
            cityService.remove(id);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
