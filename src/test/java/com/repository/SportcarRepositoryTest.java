package com.repository;

import com.model.Manufacturer;
import com.model.Sportcar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class SportcarRepositoryTest {

    private SportcarRepository target;
    private Sportcar car;

    @BeforeEach
    void setUp() {
        target = new SportcarRepository();
        car = createSportcar();
        target.save(car);
    }

    private Sportcar createSportcar() {
        return new Sportcar("Model", Manufacturer.BMW, BigDecimal.ZERO, "000");
    }

    @Test
    void getById_isFound() {
        final Optional<Sportcar> actual = target.findById(car.getId());
        Assertions.assertNotNull(actual);
        actual.ifPresent(s -> Assertions.assertEquals(car.getId(), s.getId()));
    }

    @Test
    void getById_notFound() {
        final Sportcar otherCar = createSportcar();
        final Optional<Sportcar> actual = target.findById(otherCar.getId());
        final Optional<Sportcar> expected = Optional.empty();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        final Sportcar otherCar = createSportcar();
        target.save(otherCar);
        final List<Sportcar> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void save_true() {
        final Sportcar otherCar = createSportcar();
        final boolean actual = target.save(otherCar);
        Assertions.assertTrue(actual);
    }

    @Test
    void saveAll_null() {
        try {
            final boolean actual = target.saveAll(null);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    void update() {
        final Sportcar otherCar = createSportcar();
        final boolean actual = target.update(otherCar);
        Assertions.assertFalse(actual);
        car.setMaxSpeed("1000");
        final boolean actualCar = target.update(car);
        Assertions.assertTrue(actualCar);
        final Optional<Sportcar> actualSportcar = target.findById(car.getId());
        actualSportcar.ifPresent(s -> Assertions.assertEquals("1000", s.getMaxSpeed()));
    }
}