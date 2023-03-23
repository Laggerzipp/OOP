package com.service;

import com.model.vehicle.SportCar;
import com.repository.SportCarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class SportCarServiceTest {
    private SportCarService target;
    private SportCarRepository sportcarRepository;

    @BeforeEach
    void setUp() {
        sportcarRepository = Mockito.mock(SportCarRepository.class);
        target = new SportCarService(sportcarRepository);
    }

    @Test
    void create_success() {
        //configuration
        final List<SportCar> cars = target.createAndSave(2);

        //call test method
        target.saveAll(cars);

        //checks
        Mockito.verify(sportcarRepository, Mockito.times(2)).save(Mockito.any());
    }
}