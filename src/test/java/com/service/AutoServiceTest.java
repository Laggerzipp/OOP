package com.service;

import com.model.Auto;
import com.repository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

class AutoServiceTest {
    private AutoService target;
    private AutoRepository autoRepository;

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        target = new AutoService(autoRepository);
    }

    @Test
    void create_success() {
        //configuration
        final List<Auto> autos = target.createAndSave(2);

        //call test method
        target.saveAll(autos);

        //checks
        Mockito.verify(autoRepository, Mockito.times(2)).save(Mockito.any());
    }

    @Test
    void getOrCreate_success() {
        //configuration
        final List<Auto> autos = target.createAndSave(2);

        //call test method
        final Auto auto = target.getOrCreate(autos.get(0).getId());

        //checks
        Assertions.assertNotEquals(autos.get(0).getId(), auto.getId());
    }

    @Test
    void findOneById_null() {
        //configuration
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        //call test method
        target.findOneById(null);

        //checks
        Mockito.verify(autoRepository).findById(captor.capture());
        Assertions.assertEquals("", captor.getValue());
    }
}