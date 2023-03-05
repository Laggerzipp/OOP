package com.service;

import com.model.Bus;
import com.model.Manufacturer;
import com.model.Sportcar;
import com.repository.SportcarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SportcarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final Random RANDOM = new Random();
    private final SportcarRepository sportcarRepository;

    public SportcarService(SportcarRepository sportcarRepository) {
        this.sportcarRepository = sportcarRepository;
    }

    public List<Sportcar> createSportcars(int count) {
        List<Sportcar> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Sportcar car = new Sportcar(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "Max Speed-" + (RANDOM.nextInt(1000) * 2)
            );
            result.add(car);
            sportcarRepository.save(car);
            LOGGER.debug("Created sportcar {}", car.getId());
        }
        return result;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveSportcars(List<Sportcar> sportcars) {
        sportcarRepository.saveAll(sportcars);
    }

    public void updateSportcars(Sportcar car){
        sportcarRepository.update(car);
    }
    public void deleteSportcar(Sportcar car){
        sportcarRepository.delete(car.getId());
        LOGGER.debug("Deleted sportcar {}", car.getId());
    }
    public void printAll() {
        for (Sportcar car : sportcarRepository.getAll()) {
            System.out.println(car);
        }
    }
}
