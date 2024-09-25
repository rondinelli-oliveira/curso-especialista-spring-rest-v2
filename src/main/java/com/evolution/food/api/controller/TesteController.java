package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.repository.KitchenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    private final KitchenRepository kitchenRepository;

    public TesteController(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    @GetMapping("/kitchens/all-by-name")
    public List<Kitchen> kitchensByName(@RequestParam("name") String name) {
        return kitchenRepository.findAllByName(name);
    }

    @GetMapping("/kitchens/by-name")
    public Optional<Kitchen> kitchenByName(@RequestParam("name") String name) {
        return kitchenRepository.findByName(name);
    }
}
