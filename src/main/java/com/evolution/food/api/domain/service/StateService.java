package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import com.evolution.food.api.domain.model.State;
import com.evolution.food.api.domain.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

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
            stateRepository.remove(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("Nao existe um cadastro de estado com o codigo: %d", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Estado de codigo %d nao pode ser removido, pois esta em uso", id));
        }

    }
}
