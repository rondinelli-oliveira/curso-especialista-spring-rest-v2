package com.evolution.food.api.domain.service;

import com.evolution.food.api.domain.model.Kitchen;
import com.evolution.food.api.domain.repository.KitchenRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KitchenServiceTest {

    @Autowired
    private KitchenRepository kitchenRepository;

    @Test
    void shouldRegisterKitchenWithSuccess() {
        //scenario
        Kitchen kitchen = new Kitchen();
        kitchen.setName("Brasileira");

        //action
        kitchen = kitchenRepository.save(kitchen);

        //validation

        assertThat(kitchen).isNotNull();
        assertThat(kitchen.getId()).isNotNull();
    }

    @Test
    void shouldntRegisterKitchenWithoutName() {
        Kitchen kitchen = new Kitchen();
        kitchen.setName(null);

        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    kitchenRepository.save(kitchen);
                });

        assertThat(erroEsperado).isNotNull();
    }
}
