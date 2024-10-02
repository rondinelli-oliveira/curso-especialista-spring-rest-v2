package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.StateNotFoundException;
import com.evolution.food.api.domain.model.State;
import com.evolution.food.api.domain.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StateService {

    public static final String MSG_STATE_IN_USE = "Estado de codigo %d nao pode ser removido, pois esta em uso";
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public State save(State state) {
        log.info("Persistindo estado de nome: {}", state.getName());
        return stateRepository.save(state);
    }

//    public void remove(Long id) {
//        try {
//            log.info("Deletando estado de codigo: {}", id);
//            if (!stateRepository.existsById(id)) {
//                throw new EntityNotFoundException(
//                        String.format("Nao existe um cadastro de estado com o codigo: %d", id));
//            }
//            stateRepository.deleteById(id);
//        } catch (DataIntegrityViolationException e) {
//            throw new EntityInUseException(
//                    String.format("Estado de codigo %d nao pode ser removido, pois esta em uso", id));
//        }
//
//    }

    public void remove(Long id) {
        try {
            log.info("Deletando estado de codigo: {}", id);
            if (!stateRepository.existsById(id)) {
                throw new StateNotFoundException(id);
            }
            stateRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_STATE_IN_USE, id));
        }

    }

    public State searchOrFail(Long id) {

        log.info("Pesquisando cozinha pelo codigo: {} ", id);

        State state = stateRepository.findById(id)
                .orElseThrow(() -> new StateNotFoundException(id));

        log.info("Nome da state: {}", state.getName());

        return state;
    }
}
