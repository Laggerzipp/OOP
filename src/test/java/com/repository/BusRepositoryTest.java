package com.repository;

import com.model.vehicle.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.model.vehicle.Bus;

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
        //configuration

        //call test method
        final Optional<Bus> actual = target.findById(bus.getId());

        //checks
        Assertions.assertNotNull(actual);
        actual.ifPresent(b -> Assertions.assertEquals(b.getId(), bus.getId()));
    }

    @Test
    void getById_isNotFound() {
        //configuration
        final Bus tmp = createBus();

        //call test method
        final Optional<Bus> actual = target.findById(tmp.getId());

        //checks
        final Optional<Bus> expected = Optional.empty();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        //configuration
        final Bus tmp = createBus();
        target.save(tmp);

        //call test method
        final List<Bus> actual = target.getAll();

        //checks
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void save_true() {
        //configuration
        final Bus tmp = createBus();

        //call test method
        final boolean actual = target.save(tmp);

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
    void update_notFound() {
        //configuration
        final Bus otherBus = createBus();

        //call test method
        final boolean actual = target.update(otherBus);

        //checks
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        //configuration
        bus.setManufacturer(Manufacturer.KIA);


        //call test method
        final boolean actual = target.update(bus);

        //checks
        final Optional<Bus> actualBus = target.findById(bus.getId());
        Assertions.assertTrue(actual);
        actualBus.ifPresent(b -> Assertions.assertEquals(Manufacturer.KIA, b.getManufacturer()));
    }
}