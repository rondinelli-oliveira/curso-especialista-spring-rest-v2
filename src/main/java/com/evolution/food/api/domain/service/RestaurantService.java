package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.KitchenRepository;
import com.evolution.food.api.domain.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantService {

    public static final String MSG_RESTAURANT_NOT_FOUND = "Nao existe cadastro de restaurant com codigo: %d ";
    public static final String MSG_RESTAURANT_IN_USE = "Restaurante de codigo: %d nao pode ser removido, pois esta em uso";

    private final RestaurantRepository restaurantRepository;

    private final KitchenRepository kitchenRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, KitchenRepository kitchenRepository) {
        this.restaurantRepository = restaurantRepository;
        this.kitchenRepository = kitchenRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        log.info("Persistindo restaurante de nome: {}", restaurant.getName());
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRepository.findById(kitchenId)
                .orElseThrow(() -> new EntityNotFoundException(
                    String.format("Nao existe cadastro de cozinha com codigo: %d ", kitchenId)));

        restaurant.setKitchen(kitchen);
        return restaurantRepository.save(restaurant);
    }

    public void remove(Long id) {
        try {
            log.info("Deletando restaurante de codigo: {}", id);
            if (!restaurantRepository.existsById(id)) {
                throw new com.evolution.food.api.domain.exception.EntityNotFoundException(
                        String.format(MSG_RESTAURANT_NOT_FOUND, id));
            }
            restaurantRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_RESTAURANT_IN_USE, id));
        }

    }

    public Restaurant searchOrFail(Long id) {
        log.info("Pesquisando restaurante com codigo: {}", id);

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(MSG_RESTAURANT_NOT_FOUND, id)));

        log.info("Nome do restaurante  {}", restaurant.getName());

        return restaurant;
    }
}
