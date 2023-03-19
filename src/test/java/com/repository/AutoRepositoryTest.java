package com.repository;

import com.model.Auto;
import com.model.Manufacturer;
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
        return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type");
    }

    @Test
    void getById_getOne() {
        final Optional<Auto> actual = target.findById(auto.getId());
        Assertions.assertNotNull(actual);
        actual.ifPresent(a -> Assertions.assertEquals(auto.getId(), a.getId()));
    }

    @Test
    void getAll() {
        final List<Auto> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void save_success() {
        final boolean actual = target.save(auto);
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
    void saveAll_emptyList() {
        final boolean actual = target.saveAll(Collections.emptyList());
        Assertions.assertFalse(actual);
    }

    @Test
    void update_notFound() {
        final Auto otherAuto = createSimpleAuto();
        final boolean actual = target.update(otherAuto);
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        auto.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(auto);
        Assertions.assertTrue(actual);
        final Optional<Auto> actualAuto = target.findById(auto.getId());
        actualAuto.ifPresent(a -> Assertions.assertEquals(BigDecimal.TEN, a.getPrice()));
    }
}