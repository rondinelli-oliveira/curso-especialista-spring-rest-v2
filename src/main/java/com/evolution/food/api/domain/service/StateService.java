package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import com.evolution.food.api.domain.model.State;
import com.evolution.food.api.domain.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public State save(State state) {
        log.info("Persistindo estado de nome: {}", state.getName());
        return stateRepository.save(state);
    }

    public void remove(Long id) {
        try {
            log.info("Deletando estado de codigo: {}", id);
            if (!stateRepository.existsById(id)) {
                throw new EntityNotFoundException(
                        String.format("Nao existe um cadastro de estado com o codigo: %d", id));
            }
            stateRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Estado de codigo %d nao pode ser removido, pois esta em uso", id));
        }

    }
}
