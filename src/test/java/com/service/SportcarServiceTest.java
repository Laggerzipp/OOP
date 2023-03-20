package com.service;

import com.model.Manufacturer;
import com.model.Sportcar;
import com.repository.SportcarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
        final List<Sportcar> cars = target.createSportcars(3);
        final Sportcar car = cars.get(1);
        car.setMaxSpeed("555");
        target.updateSportcars(car);

        final Optional<Sportcar> actual = sportcarRepository.findById(car.getId());
        actual.ifPresent(c -> Assertions.assertEquals("555", c.getMaxSpeed()));
    }

    @Test
    void deleteSportca_true() {
        final List<Sportcar> cars = target.createSportcars(3);
        final Optional<Sportcar> actual = sportcarRepository.findById(cars.get(2).getId());

        actual.ifPresent(c -> Assertions.assertTrue(target.deleteSportcar(c)));

        final List<Sportcar> actualCars = sportcarRepository.getAll();
        Assertions.assertEquals(2, actualCars.size());
    }

    @Test
    void deleteSportcar_false() {
        final List<Sportcar> cars = target.createSportcars(3);
        final Optional<Sportcar> actual = Optional.of(createSportcar());

        actual.ifPresent(c -> Assertions.assertFalse(target.deleteSportcar(c)));

        final List<Sportcar> actualCars = sportcarRepository.getAll();
        Assertions.assertEquals(3, actualCars.size());
    }
}