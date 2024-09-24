package com.evolution.food.api.controller;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import com.evolution.food.api.domain.model.State;
import com.evolution.food.api.domain.repository.StateRepository;
import com.evolution.food.api.domain.service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
@Slf4j
public class StateController {

    private final StateRepository stateRepository;

    private final StateService stateService;

    public StateController(StateRepository stateRepository, StateService stateService) {
        this.stateRepository = stateRepository;
        this.stateService = stateService;
    }

    @GetMapping
    public List<State> findAll() {
        List<State> states = stateRepository.findAll();
        for (State state: states) {
            log.info("O nome do estado de codigo: {} e {}", state.getId(), state.getName());
        }
        return stateRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findById(@PathVariable Long id) {
        State state = stateRepository.findById(id);

        if (null != state) {
            log.info("Pesquisando estado com codigo: {}", id);
            log.info("Nome do estado  {}", state.getName());
            return ResponseEntity.ok(state);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State add(@RequestBody State state) {
        return stateService.save(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State state) {
        State currentState = stateRepository.findById(id);

        if (null != currentState) {
            log.info("Atualizando estado de codigo: {} e nome {}, para {}", currentState.getId(), currentState.getName(),
                    state.getName());
            BeanUtils.copyProperties(state, currentState, "id");
            stateService.save(currentState);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {
            stateService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }

    }
}
