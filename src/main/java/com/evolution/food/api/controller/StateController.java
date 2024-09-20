package com.evolution.food.api.controller;

import com.evolution.food.api.domain.model.State;
import com.evolution.food.api.domain.repository.StateRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/states")
@Log4j2
public class StateController {

    private final StateRepository stateRepository;

    public StateController(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @GetMapping
    public List<State> findAll() {
        List<State> states = stateRepository.findAll();
        for (State state: states) {
            log.info("O nome do estado de codigo: {} e {}", state.getId(), state.getName());
        }
        return stateRepository.findAll();
    }
}
