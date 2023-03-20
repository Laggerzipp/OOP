package com.service;

import com.model.Manufacturer;
import com.model.SportCar;
import com.repository.SportCarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SportCarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final Random RANDOM = new Random();
    private final SportCarRepository sportcarRepository;

    public SportCarService(SportCarRepository sportcarRepository) {
        this.sportcarRepository = sportcarRepository;
    }

    public List<SportCar> createSportCars(int count) {
        List<SportCar> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            SportCar car = new SportCar(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "" + (RANDOM.nextInt(1000) * 2)
            );
            result.add(car);
            try {
                sportcarRepository.save(car);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            LOGGER.debug("Created sport car {}", car.getId());
        }
        return result;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveSportCars(List<SportCar> sportCars) {
        try {
            sportcarRepository.saveAll(sportCars);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        LOGGER.debug("Saved {} sport cars", sportCars.size());
    }

    public void updateSportCars(SportCar car) {
        try {
            sportcarRepository.findById(car.getId())
                    .orElseThrow(() -> new IllegalStateException("Sport car with id " + car.getId() + " not found"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sportcarRepository.update(car);
        LOGGER.debug("Updated sport car {}", car.getId());
    }

    public boolean deleteSportCar(SportCar car) {
        try {
            sportcarRepository.findById(car.getId())
                    .orElseThrow(() -> new IllegalStateException("Sport car with id " + car.getId() + " not found"));
            sportcarRepository.delete(car.getId());
            LOGGER.debug("Deleted sport car {}", car.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void printAll() {
        for (SportCar car : sportcarRepository.getAll()) {
            System.out.println(car);
        }
    }
}
