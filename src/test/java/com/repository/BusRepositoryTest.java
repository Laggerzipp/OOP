package com.repository;

import com.model.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.model.Bus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class BusRepositoryTest {
    private BusRepository target;
    private Bus bus;

    @BeforeEach
    void setUp() {
        target = new BusRepository();
        bus = createBus();
        target.save(bus);
    }

    private Bus createBus() {
        return new Bus("Model", Manufacturer.ZAZ, BigDecimal.ZERO, "Line-000", 1);
    }

    @Test
    void getById_isOne() {
        final Optional<Bus> actual = target.findById(bus.getId());
        Assertions.assertNotNull(actual);
        actual.ifPresent(b -> Assertions.assertEquals(b.getId(), bus.getId()));
    }

    @Test
    void getById_isNotFound() {
        final Bus tmp = createBus();
        final Optional<Bus> actual = target.findById(tmp.getId());
        final Optional<Bus> expected = Optional.empty();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        final Bus tmp = createBus();
        target.save(tmp);
        final List<Bus> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void save_true() {
        final Bus tmp = createBus();
        final boolean actual = target.save(tmp);
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
    void update_notFound() {
        final Bus otherBus = createBus();
        final boolean actual = target.update(otherBus);
        Assertions.assertFalse(actual);
    }

    @Test
    void update_true() {
        bus.setManufacturer(Manufacturer.KIA);
        final boolean actual = target.update(bus);
        Assertions.assertTrue(actual);
        final Optional<Bus> actualBus = target.findById(bus.getId());
        actualBus.ifPresent(b -> Assertions.assertEquals(Manufacturer.KIA, b.getManufacturer()));
    }
}