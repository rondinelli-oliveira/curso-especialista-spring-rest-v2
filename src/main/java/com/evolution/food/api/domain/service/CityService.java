package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import com.evolution.food.api.domain.model.City;
import com.evolution.food.api.domain.model.State;
import com.evolution.food.api.domain.repository.CityRepository;
import com.evolution.food.api.domain.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CityService {

    private final CityRepository cityRepository;

    private final StateRepository stateRepository;

    public CityService(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    public City save(City city) {
        log.info("Persistindo cidade de nome: {}", city.getName());
        Long stataId = city.getState().getId();
        State state = stateRepository.findById(stataId);

        if (state == null) {
            throw new EntityNotFoundException(
                    String.format("Não existe cadastro de estado com código %d", stataId));
        }

        city.setState(state);

        return cityRepository.save(city);
    }

    public void remove(Long id) {
        try {
            log.info("Deletando cidade de codigo: {}", id);
            cityRepository.remove(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("Nao existe um cadastro de city com codigo %d", id));

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Cidade de codigo %d nao pode ser removida, pois esta em uso", id));
        }
    }
}
