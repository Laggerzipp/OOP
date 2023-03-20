package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final Random RANDOM = new Random();
    private final AutoRepository autoRepository;

    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public List<Auto> createAutos(int count) {
        List<Auto> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Auto auto = new Auto(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "Model-" + RANDOM.nextInt(1000)
            );
            result.add(auto);
            try {
                autoRepository.save(auto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveAutos(List<Auto> autos) {
        try {
            autoRepository.saveAll(autos);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        LOGGER.debug("Saved {} autos", autos.size());
    }

    public List<Auto> getAll() {
        return autoRepository.getAll();
    }

    public void updateAuto(Auto auto) {
        try {
            autoRepository.findById(auto.getId())
                    .orElseThrow(() -> new IllegalStateException("Auto with id " + auto.getId() + " not found"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        autoRepository.update(auto);
        LOGGER.debug("Updated auto {}", auto.getId());
    }

    public void deleteAuto(Auto auto) {
        try {
            autoRepository.findById(auto.getId()).orElseThrow(() -> new IllegalStateException("Auto with id " + auto.getId() + " not found"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        autoRepository.delete(auto.getId());
        LOGGER.debug("Deleted auto {}", auto.getId());
    }

    public void printAll() {
        for (Auto auto : autoRepository.getAll()) {
            System.out.println(auto);
        }
    }

    public void findOneById(String id) {
        if (id == null) {
            autoRepository.findById("");
        } else {
            autoRepository.findById(id);
        }
    }
}
