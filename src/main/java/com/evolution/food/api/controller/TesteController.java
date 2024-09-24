package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.repository.KitchenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteController {

    private final KitchenRepository kitchenRepository;

    public TesteController(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    @GetMapping("/kitchens/by-name")
    public List<Kitchen> kitchensByName(@RequestParam("name") String name) {
        return kitchenRepository.findByName(name);
    }
}
