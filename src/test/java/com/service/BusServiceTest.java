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
import java.util.Optional;

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
        final Bus bus1 = createBus();
        final Bus bus2 = createBus();
        final List<Bus> actual = new LinkedList<>();
        actual.add(bus1);
        actual.add(bus2);
        target.saveBuses(actual);
        final List<Bus> actualBuses = busRepository.getAll();
        Assertions.assertEquals(actual.size(), actualBuses.size());
    }

    @Test
    void updateBus() {
        final List<Bus> buses = target.createBuses(3);
        final Bus bus = buses.get(1);
        bus.setLineName("555");
        target.updateBuses(bus);

        final Optional<Bus> actual = busRepository.findById(bus.getId());
        actual.ifPresent(b -> Assertions.assertEquals("555", b.getLineName()));
    }

     @Test
    void deleteBus() {
        final List<Bus> buses = target.createBuses(3);
        final Optional<Bus> actual = busRepository.findById(buses.get(2).getId());

         actual.ifPresent(b -> target.deleteBuses(b));

        final List<Bus> actualCars = busRepository.getAll();
        Assertions.assertEquals(2, actualCars.size());
    }
}