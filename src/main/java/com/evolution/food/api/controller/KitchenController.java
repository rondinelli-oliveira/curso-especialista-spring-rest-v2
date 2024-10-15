package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.repository.KitchenRepository;
import com.evolution.food.api.domain.service.KitchenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchens")
@Slf4j
public class KitchenController {

    private final KitchenRepository kitchenRepository;

    private final KitchenService kitchenService;

    public KitchenController(KitchenRepository kitchenRepository, KitchenService kitchenService) {
        this.kitchenRepository = kitchenRepository;
        this.kitchenService = kitchenService;
    }

    @GetMapping
    public List<Kitchen> findAll() {
    	List<Kitchen> kitchens = kitchenRepository.findAll();
    	for(Kitchen kitchen: kitchens) {
            log.info("Nome da cozinha de codigo: {} e {}", kitchen.getId(), kitchen.getName());
    	}
        return kitchens;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Kitchen> findById(@PathVariable Long id) {
//    	Optional<Kitchen> kitchen = kitchenRepository.findById(id);
//
//        if (kitchen.isPresent() ) {
//            log.info("Pesquisando cozinha pelo codigo: {} ", id);
//            log.info("Nome da cozinha: {}", kitchen.get().getName());
//            return ResponseEntity.status(HttpStatus.OK).body(kitchen.get());
//        }
//
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/{id}")
    public Kitchen findById(@PathVariable Long id) {
        return kitchenService.searchOrFail(id);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Kitchen add(@RequestBody Kitchen kitchen) {
//        return kitchenService.save(kitchen);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen) {
//        Optional<Kitchen> currentKitchen = kitchenRepository.findById(id);
//
//        if (currentKitchen.isPresent()) {
//            log.info("Atualizando cozinha de codigo: {} e nome {}, para {}", currentKitchen.get().getId(), currentKitchen.get().getName(),
//                    kitchen.getName());
//            BeanUtils.copyProperties(kitchen, currentKitchen.get(), "id");
//            Kitchen saveKitchen = kitchenService.save(currentKitchen.get());
//            return ResponseEntity.ok(saveKitchen);
//        }
//        return ResponseEntity.notFound().build();
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen add(@RequestBody @Valid Kitchen kitchen) {
        return kitchenService.save(kitchen);
    }

    @PutMapping("/{id}")
    public Kitchen update(@PathVariable Long id, @RequestBody Kitchen kitchen) {
        Kitchen currentKitchen = kitchenService.searchOrFail(id);

        log.info("Atualizando cozinha de codigo: {} e nome {}, para {}", currentKitchen.getId(), currentKitchen.getName(),
                kitchen.getName());
        BeanUtils.copyProperties(kitchen, currentKitchen, "id");

        return kitchenService.save(currentKitchen);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> remove(@PathVariable Long id) {
//        try {
//            kitchenService.remove(id);
//            return ResponseEntity.noContent().build();
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//
//        } catch (EntityInUseException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(e.getMessage());
//        }
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        kitchenService.remove(id);

    }

}
