package com.repository;

import com.model.Manufacturer;
import com.model.Sportcar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.FileNameMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        final Sportcar actual = target.getById(car.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(car.getId(), actual.getId());
    }

    @Test
    void getById_notFound() {
        final Sportcar otherCar = createSportcar();
        final Sportcar actual = target.getById(otherCar.getId());
        Assertions.assertNull(actual);
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
        final boolean actual = target.saveAll(null);
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        final Sportcar otherCar = createSportcar();
        final boolean actual = target.update(otherCar);
        Assertions.assertFalse(actual);
        car.setMaxSpeed("1000");
        final boolean actualCar = target.update(car);
        Assertions.assertTrue(actualCar);
        final Sportcar actualSportcar = target.getById(car.getId());
        Assertions.assertEquals("1000",actualSportcar.getMaxSpeed());
    }
}