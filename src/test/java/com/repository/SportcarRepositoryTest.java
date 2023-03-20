package com.repository;

import com.model.Manufacturer;
import com.model.SportCar;
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
        final Optional<SportCar> actual = target.findById(car.getId());
        Assertions.assertNotNull(actual);
        actual.ifPresent(s -> Assertions.assertEquals(car.getId(), s.getId()));
    }

    @Test
    void getById_notFound() {
        final SportCar otherCar = createSportcar();
        final Optional<SportCar> actual = target.findById(otherCar.getId());
        final Optional<SportCar> expected = Optional.empty();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        final SportCar otherCar = createSportcar();
        target.save(otherCar);
        final List<SportCar> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void save_true() {
        final SportCar otherCar = createSportcar();
        final boolean actual = target.save(otherCar);
        Assertions.assertTrue(actual);
    }

    @Test
    void save_null() {
        Assertions.assertThrows(IllegalStateException.class, () -> target.save(null));
    }

    @Test
    void saveAll_null() {
        Assertions.assertThrows(IllegalStateException.class, () -> target.save(null));
    }

    @Test
    void update() {
        final SportCar otherCar = createSportcar();
        final boolean actual = target.update(otherCar);
        Assertions.assertFalse(actual);
        car.setMaxSpeed("1000");
        final boolean actualCar = target.update(car);
        Assertions.assertTrue(actualCar);
        final Optional<SportCar> actualSportCar = target.findById(car.getId());
        actualSportCar.ifPresent(s -> Assertions.assertEquals("1000", s.getMaxSpeed()));
    }
}