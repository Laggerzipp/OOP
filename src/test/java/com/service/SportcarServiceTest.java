package com.service;

import com.model.Manufacturer;
import com.model.SportCar;
import com.repository.SportCarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

class SportCarServiceTest {
    private SportCarService target;
    private SportCarRepository sportcarRepository;

    @BeforeEach
    void setUp() {
        sportcarRepository = new SportCarRepository();
        target = new SportCarService(sportcarRepository);
    }

    private SportCar createSportcar() {
        return new SportCar("Model", Manufacturer.ZAZ, BigDecimal.ZERO, "000");
    }

    @Test
    void createSportCars_zeroCount() {
        final List<SportCar> actual = target.createSportCars(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createSportCars_negativeCount() {
        final List<SportCar> actual = target.createSportCars(-2);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createSportCars_positiveCount() {
        final List<SportCar> actual = target.createSportCars(3);
        Assertions.assertEquals(3, actual.size());
    }

    @Test
    void saveSportCars() {
        final SportCar tmp1 = createSportcar();
        final SportCar tmp2 = createSportcar();
        final List<SportCar> actual = new LinkedList<>();
        actual.add(tmp1);
        actual.add(tmp2);
        target.saveSportCars(actual);
        final List<SportCar> actualCars = sportcarRepository.getAll();
        Assertions.assertEquals(actual.size(), actualCars.size());
    }

    @Test
    void updateSportCars() {
        final List<SportCar> cars = target.createSportCars(3);
        final SportCar car = cars.get(1);
        car.setMaxSpeed("555");
        target.updateSportCars(car);

        final Optional<SportCar> actual = sportcarRepository.findById(car.getId());
        actual.ifPresent(c -> Assertions.assertEquals("555", c.getMaxSpeed()));
    }

    @Test
    void deleteSportCar_true() {
        final List<SportCar> cars = target.createSportCars(3);
        final Optional<SportCar> actual = sportcarRepository.findById(cars.get(2).getId());

        actual.ifPresent(c -> Assertions.assertTrue(target.deleteSportCar(c)));

        final List<SportCar> actualCars = sportcarRepository.getAll();
        Assertions.assertEquals(2, actualCars.size());
    }

    @Test
    void deleteSportCar_false() {
        target.createSportCars(3);
        final Optional<SportCar> actual = Optional.of(createSportcar());

        actual.ifPresent(c -> Assertions.assertFalse(target.deleteSportCar(c)));

        final List<SportCar> actualCars = sportcarRepository.getAll();
        Assertions.assertEquals(3, actualCars.size());
    }
}