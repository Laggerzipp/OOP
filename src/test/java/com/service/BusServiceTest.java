package com.service;

import com.model.Bus;
import com.repository.BusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class BusServiceTest {
    private BusService target;
    private BusRepository busRepository;

    @BeforeEach
    void setUp() {
        busRepository = new BusRepository();
        target = new BusService(busRepository);
    }

    @Test
    void create_success() {
        final Bus bus1 = target.create();
        final Bus bus2 = target.create();
        busRepository.save(bus1);
        busRepository.save(bus2);

        final Bus expected = busRepository.getAll().get(0);
        Assertions.assertEquals(expected.getId(), bus1.getId());

        final List<Bus> buses = busRepository.getAll();
        Assertions.assertEquals(2, buses.size());
    }
}