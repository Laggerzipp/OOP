package com.repository;

import com.model.vehicle.Manufacturer;
import com.model.vehicle.SportCar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class SportCarRepositoryTest {

    private SportCarRepository target;
    private SportCar car;

    @BeforeEach
    void setUp() {
        target = new SportCarRepository();
        car = createSportcar();
        target.save(car);
    }

    private SportCar createSportcar() {
        return new SportCar("Model", Manufacturer.BMW, BigDecimal.ZERO, "000", 1);
    }

    @Test
    void getById_isFound() {
        //configuration
        final Optional<SportCar> actual = target.findById(car.getId());

        //call test method and checks
        Assertions.assertNotNull(actual);
        actual.ifPresent(s -> Assertions.assertEquals(car.getId(), s.getId()));
    }

    @Test
    void getById_notFound() {
        //configuration
        final SportCar otherCar = createSportcar();
        final Optional<SportCar> expected = Optional.empty();

        //call test method
        final Optional<SportCar> actual = target.findById(otherCar.getId());

        //checks
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        //configuration
        final SportCar otherCar = createSportcar();
        target.save(otherCar);

        //call test method
        final List<SportCar> actual = target.getAll();

        //checks
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void save_true() {
        //configuration
        final SportCar otherCar = createSportcar();

        //call test method
        final boolean actual = target.save(otherCar);

        //checks
        Assertions.assertTrue(actual);
    }

    @Test
    void save_null() {
        //configuration

        //call test method and checks
        Assertions.assertThrows(IllegalStateException.class, () -> target.save(null));
    }

    @Test
    void saveAll_null() {
        //configuration

        //call test method and checks
        Assertions.assertThrows(IllegalStateException.class, () -> target.save(null));
    }

    @Test
    void update() {
        //configuration
        final SportCar otherCar = createSportcar();
        car.setMaxSpeed("1000");
        final Optional<SportCar> actualSportCar = target.findById(car.getId());
        //call test method
        final boolean actual = target.update(otherCar);
        final boolean actualCar = target.update(car);

        //checks
        Assertions.assertFalse(actual);
        Assertions.assertTrue(actualCar);
        actualSportCar.ifPresent(s -> Assertions.assertEquals("1000", s.getMaxSpeed()));
    }
}