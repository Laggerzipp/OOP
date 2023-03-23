package com.repository;

import com.model.vehicle.Auto;
import com.model.vehicle.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class AutoRepositoryTest {
    private AutoRepository target;

    private Auto auto;

    @BeforeEach
    void setUp() {
        target = new AutoRepository();
        auto = createSimpleAuto();
        target.save(auto);
    }

    private Auto createSimpleAuto() {
        return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type", 1);
    }

    @Test
    void getById_getOne() {
        //configuration

        //call test method
        final Optional<Auto> actual = target.findById(auto.getId());

        //checks
        Assertions.assertNotNull(actual);
        actual.ifPresent(a -> Assertions.assertEquals(auto.getId(), a.getId()));
    }

    @Test
    void getAll() {
        //configuration

        //call test method
        final List<Auto> actual = target.getAll();

        //checks
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void save_success() {
        //configuration

        //call test method
        final boolean actual = target.save(auto);

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
    void saveAll_emptyList() {
        //configuration

        //call test method
        final boolean actual = target.saveAll(Collections.emptyList());

        //checks
        Assertions.assertFalse(actual);
    }

    @Test
    void update_notFound() {
        //configuration
        final Auto otherAuto = createSimpleAuto();

        //call test method
        final boolean actual = target.update(otherAuto);

        //checks
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        //configuration
        auto.setPrice(BigDecimal.TEN);

        //call test method
        final boolean actual = target.update(auto);

        //checks
        Assertions.assertTrue(actual);
        final Optional<Auto> actualAuto = target.findById(auto.getId());
        actualAuto.ifPresent(a -> Assertions.assertEquals(BigDecimal.TEN, a.getPrice()));
    }
}