package com.service;

import com.model.SportCar;
import com.repository.SportCarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class SportCarServiceTest {
    private SportCarService target;
    private SportCarRepository sportcarRepository;

    @BeforeEach
    void setUp() {
        sportcarRepository = new SportCarRepository();
        target = new SportCarService(sportcarRepository);
    }

    @Test
    void create_success() {
        final SportCar car1 = target.create();
        final SportCar car2 = target.create();
        sportcarRepository.save(car1);
        sportcarRepository.save(car2);

        final SportCar expected = sportcarRepository.getAll().get(0);
        Assertions.assertEquals(expected.getId(), car1.getId());

        final List<SportCar> cars = sportcarRepository.getAll();
        Assertions.assertEquals(2, cars.size());
    }
}