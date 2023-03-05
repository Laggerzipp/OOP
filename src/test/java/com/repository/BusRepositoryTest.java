package com.repository;

import com.model.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.model.Bus;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        return new Bus("Model", Manufacturer.ZAZ, BigDecimal.ZERO, "Line-000");
    }

    @Test
    void getById_isOne() {
        final Bus actual = target.getById(bus.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getId(), bus.getId());
    }

    @Test
    void getById_isNull() {
        final Bus tmp = createBus();
        final Bus actual = target.getById(tmp.getId());
        Assertions.assertNull(actual);
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
    void saveAll_isFalse() {
        final boolean actual = target.saveAll(null);
        Assertions.assertFalse(actual);
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
    }

    @Test
    void delete() {
    }
}