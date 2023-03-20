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
        final List<Auto> autos = target.createAndSave(2);
        target.saveAll(autos);

        Mockito.verify(autoRepository, Mockito.times(2)).save(Mockito.any());
    }

    @Test
    void getOrCreate_success() {
        final List<Auto> autos = target.createAndSave(2);
        final Auto auto = target.getOrCreate(autos.get(0).getId());

        Assertions.assertNotEquals(autos.get(0).getId(), auto.getId());
    }

    @Test
    void findOneById_null() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById(null);
        Mockito.verify(autoRepository).findById(captor.capture());
        Assertions.assertEquals("", captor.getValue());
    }
}