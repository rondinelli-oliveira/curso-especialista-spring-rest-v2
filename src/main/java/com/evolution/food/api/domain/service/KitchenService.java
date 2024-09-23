package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.repository.KitchenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KitchenService {

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
            kitchenRepository.remove(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("Nao existe cadastro de cozinha com codigo: %d ", id));

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Cozinha de codigo: %d nao pode ser removida, pois esta em uso", id));
        }

    }
}
