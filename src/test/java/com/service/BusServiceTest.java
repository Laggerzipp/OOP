package com.service;

import com.model.vehicle.Bus;
import com.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class BusServiceTest {
    private BusService target;
    private BusRepository busRepository;

    @BeforeEach
    void setUp() {
        busRepository = Mockito.mock(BusRepository.class);
        target = new BusService(busRepository);
    }

    @Test
    void create_success() {
        //configuration
        final List<Bus> buses = target.createAndSave(2);

        //call test method
        target.saveAll(buses);

        //checks
        Mockito.verify(busRepository, Mockito.times(2)).save(Mockito.any());
    }
}