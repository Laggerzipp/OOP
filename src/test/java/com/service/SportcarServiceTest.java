package com.service;

import com.model.Bus;
import com.model.Manufacturer;
import com.model.Sportcar;
import com.repository.SportcarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SportcarServiceTest {
    private SportcarService target;
    private SportcarRepository sportcarRepository;

    @BeforeEach
    void setUp() {
        sportcarRepository = new SportcarRepository();
        target = new SportcarService(sportcarRepository);
    }

    private Sportcar createSportcar() {
        return new Sportcar("Model", Manufacturer.ZAZ, BigDecimal.ZERO, "000");
    }

    @Test
    void createSportcars_zeroCount() {
        final List<Sportcar> actual = target.createSportcars(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createSportcars_negativeCount() {
        final List<Sportcar> actual = target.createSportcars(-2);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createSportcars_positiveCount() {
        final List<Sportcar> actual = target.createSportcars(3);
        Assertions.assertEquals(3, actual.size());
    }

    @Test
    void saveSportcars() {
        final Sportcar tmp1 = createSportcar();
        final Sportcar tmp2 = createSportcar();
        final List<Sportcar> actual = new LinkedList<>();
        actual.add(tmp1);
        actual.add(tmp2);
        target.saveSportcars(actual);
        final List<Sportcar> actualCars = sportcarRepository.getAll();
        Assertions.assertEquals(actual.size(), actualCars.size());
    }

    @Test
    void updateSportcars() {
        final Sportcar car = createSportcar();
        final List<Sportcar> cars = new LinkedList<>();
        cars.add(car);
        target.saveSportcars(cars);

        car.setMaxSpeed("555");
        target.updateSportcars(car);
        final Sportcar actual = sportcarRepository.getById(car.getId());
        Assertions.assertEquals("555", actual.getMaxSpeed());
    }

    @Test
    void deleteSportcar() {
        final Sportcar car = createSportcar();
        final Sportcar car2 = createSportcar();
        final List<Sportcar> cars = new LinkedList<>();
        cars.add(car);
        cars.add(car2);
        target.saveSportcars(cars);

        final Sportcar actual = sportcarRepository.getById(car.getId());
        Assertions.assertNotNull(actual);

        target.deleteSportcar(car);
        final List<Sportcar> actualCars = sportcarRepository.getAll();
        Assertions.assertEquals(1, actualCars.size());
    }
}