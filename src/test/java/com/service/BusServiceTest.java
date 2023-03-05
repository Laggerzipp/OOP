package com.service;

import com.model.Bus;
import com.model.Manufacturer;
import com.repository.BusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BusServiceTest {
    private BusService target;
    private BusRepository busRepository;

    @BeforeEach
    void setUp() {
        busRepository = new BusRepository();
        target = new BusService(busRepository);
    }

    private Bus createBus() {
        return new Bus("Model", Manufacturer.ZAZ, BigDecimal.ZERO, "Line-000");
    }

    @Test
    void createBuses_zeroNegativeCount() {
        final List<Bus> actual = target.createBuses(0);
        Assertions.assertEquals(0, actual.size());
        final List<Bus> actualBus = target.createBuses(-2);
        Assertions.assertEquals(0, actualBus.size());
    }

    @Test
    void createBuses_positiveCount() {
        final List<Bus> actual = target.createBuses(10);
        Assertions.assertEquals(10, actual.size());
    }

    @Test
    void saveBuses() {
        final Bus tmp1 = createBus();
        final Bus tmp2 = createBus();
        final List<Bus> actual = new LinkedList<>();
        actual.add(tmp1);
        actual.add(tmp2);
        target.saveBuses(actual);
        final List<Bus> actualBuses = busRepository.getAll();
        Assertions.assertEquals(actual.size(), actualBuses.size());
    }

    @Test
    void updateBus() {
        final Bus bus = createBus();
        final List<Bus> buses = new LinkedList<>();
        buses.add(bus);
        target.saveBuses(buses);

        bus.setLineName("555");
        target.updateBuses(bus);
        final Bus actual = busRepository.getById(bus.getId());
        Assertions.assertEquals("555", actual.getLineName());
    }

    @Test
    void deleteBus() {
        final Bus bus = createBus();
        final Bus bus2 = createBus();
        final List<Bus> buses = new LinkedList<>();
        buses.add(bus);
        buses.add(bus2);
        target.saveBuses(buses);

        final Bus actual = busRepository.getById(bus.getId());
        Assertions.assertNotNull(actual);

        target.deleteBuses(bus);
        final List<Bus> actualCars = busRepository.getAll();
        Assertions.assertEquals(1, actualCars.size());
    }
}