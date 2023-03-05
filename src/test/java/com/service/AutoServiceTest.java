package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutoServiceTest {
    private AutoService target;
    private AutoRepository autoRepository;

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        target = new AutoService(autoRepository);
    }

    @Test
    void createAutos_negativeCount() {
        final List<Auto> actual = target.createAutos(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos_zeroCount() {
        final List<Auto> actual = target.createAutos(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos() {
        final List<Auto> actual = target.createAutos(5);
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(autoRepository, Mockito.times(5))
                .save(Mockito.any());
    }

    @Test
    void saveAutos() {
    }

    @Test
    void printAll() {
        List<Auto> autos = List.of(createSimpleAuto(), createSimpleAuto());
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
        target.printAll();
    }

    @Test
    void updateAuto() {
        final List<Auto> autos = List.of(createSimpleAuto());
        autos.get(0).setBodyType("555");
        //Mockito.when(autoRepository.getById(autos.get(0).getId())).thenReturn()
    }

    @Test
    void deleteAuto() {
        final Auto auto = createSimpleAuto();
        final Auto auto2 = createSimpleAuto();
        final List<Auto> autos = new LinkedList<>();
        autos.add(auto);
        autos.add(auto2);
        target.saveAutos(autos);

        final Auto actual = autoRepository.getById(auto.getId());
        Assertions.assertNotNull(actual);

        target.deleteAuto(auto);
        final List<Auto> actualCars = autoRepository.getAll();
        Assertions.assertEquals(1, actualCars.size());
    }

    private Auto createSimpleAuto() {
        return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type");
    }

}