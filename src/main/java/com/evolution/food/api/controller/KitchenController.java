package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.model.KitchenXmlWrapper;
import com.evolution.food.api.domain.repository.KitchenRepository;
import com.evolution.food.api.domain.service.KitchenService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        return kitchenRepository.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public KitchenXmlWrapper findAllXml() {
        return new KitchenXmlWrapper(kitchenRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> findById(@PathVariable Long id) {
    	Kitchen kitchen = kitchenRepository.findById(id);

        if (null != kitchen ) {
            log.info("Pesquisando cozinha pelo codigo: {} ", id);
            log.info("Nome da cozinha: {}", kitchen.getName());
            return ResponseEntity.status(HttpStatus.OK).body(kitchen);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen add(@RequestBody Kitchen kitchen) {
        return kitchenService.save(kitchen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen) {
        Kitchen currentKitchen = kitchenRepository.findById(id);

        if (null != currentKitchen) {
            log.info("Atualizando cozinha de codigo: {} e nome {}, para {}", currentKitchen.getId(), currentKitchen.getName(),
                    kitchen.getName());
            BeanUtils.copyProperties(kitchen, currentKitchen, "id");
            kitchenRepository.save(currentKitchen);
            return ResponseEntity.ok(currentKitchen);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kitchen> remove(@PathVariable Long id) {
        try {
            Kitchen kitchen = kitchenRepository.findById(id);

            if (null != kitchen) {
                log.info("Deletando cozinha de codigo: {}", kitchen.getId());
                kitchenRepository.remove(kitchen);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
