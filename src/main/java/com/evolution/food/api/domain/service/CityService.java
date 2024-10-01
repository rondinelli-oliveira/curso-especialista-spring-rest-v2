package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import com.evolution.food.api.domain.model.City;
import com.evolution.food.api.domain.model.State;
import com.evolution.food.api.domain.repository.CityRepository;
import com.evolution.food.api.domain.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CityService {

    private static final String MSG_CITY_NOT_FOUND = "Não existe cadastro de cidade com código %d";
    public static final String MSG_CITY_IN_USE = "Cidade de codigo %d nao pode ser removida, pois esta em uso";
    public static final String MSG_STATE_NOT_FOUND = "Não existe cadastro de estado com código %d";

    private final CityRepository cityRepository;

    private final StateRepository stateRepository;

    public CityService(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

//    public City save(City city) {
//        log.info("Persistindo cidade de nome: {}", city.getName());
//        Long stataId = city.getState().getId();
//        State state = stateRepository.findById(stataId)
//                .orElseThrow(() -> new EntityNotFoundException(
//                        String.format("Não existe cadastro de estado com código %d", stataId)));
//
//        city.setState(state);
//
//        return cityRepository.save(city);
//    }

    public City save(City city) {
        log.info("Persistindo cidade de nome: {}", city.getName());
        Long stataId = city.getState().getId();
        State state = stateRepository.findById(stataId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(MSG_STATE_NOT_FOUND, stataId)));

        city.setState(state);

        return cityRepository.save(city);
    }

//    public void remove(Long id) {
//        try {
//            log.info("Deletando cidade de codigo: {}", id);
//            if (!cityRepository.existsById(id)) {
//                throw new EntityNotFoundException(
//                        String.format("Não existe cadastro de cidade com código %d", id));
//            }
//            cityRepository.deleteById(id);
//
//        } catch (DataIntegrityViolationException e) {
//            throw new EntityInUseException(
//                    String.format("Cidade de codigo %d nao pode ser removida, pois esta em uso", id));
//        }
//    }

    public void remove(Long id) {
        try {
            log.info("Deletando cidade de codigo: {}", id);
            if (!cityRepository.existsById(id)) {
                throw new EntityNotFoundException(
                        String.format(MSG_CITY_NOT_FOUND, id));
            }
            cityRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_CITY_IN_USE, id));
        }
    }

    public City searchOrFail(Long id) {

        log.info("Pesquisando cidade pelo codigo: {} ", id);

        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(MSG_CITY_NOT_FOUND, id)));

        log.info("Nome da cidade: {}", city.getName());

        return city;
    }
}
