package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.repository.KitchenRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kitchens")
@Log4j2
public class KitchenController {

    private final KitchenRepository kitchenRepository;

    public KitchenController(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    @GetMapping
    public List<Kitchen> findAll() {
    	List<Kitchen> kitchens = kitchenRepository.findAll();
    	for(Kitchen kitchen: kitchens) {
            log.info("Nome da cozinha de codigo: {} e {}", kitchen.getId(), kitchen.getName());
    	}
        return kitchenRepository.findAll();
    }
}
