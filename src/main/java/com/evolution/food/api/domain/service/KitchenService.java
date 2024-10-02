package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.KitchenNotFoundException;
import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.repository.KitchenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KitchenService {

    public static final String MSG_KITCHEN_IN_USE = "Cozinha de codigo: %d nao pode ser removida, pois esta em uso";

    private final KitchenRepository kitchenRepository;

    public KitchenService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    public Kitchen save(Kitchen kitchen) {
        log.info("Persistindo cozinha de nome: {}", kitchen.getName());
        return kitchenRepository.save(kitchen);
    }

    public void remove(Long id) {
        try {
            log.info("Deletando cozinha de codigo: {}", id);
            if (!kitchenRepository.existsById(id)) {
                throw new KitchenNotFoundException(id);
            }
            kitchenRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_KITCHEN_IN_USE, id));
        }

    }

    public Kitchen searchOrFail(Long id) {

        log.info("Pesquisando cozinha pelo codigo: {} ", id);

        Kitchen kitchen = kitchenRepository.findById(id)
                .orElseThrow(() -> new KitchenNotFoundException(id));

        log.info("Nome da cozinha: {}", kitchen.getName());

        return kitchen;
    }
}
