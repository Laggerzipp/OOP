package com.service;

import com.model.Bus;
import com.model.Manufacturer;
import com.repository.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final Random RANDOM = new Random();
    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public List<Bus> createBuses(int count) {
        List<Bus> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Bus bus = new Bus(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "Line-" + RANDOM.nextInt(1000)
            );
            result.add(bus);
            try {
                busRepository.save(bus);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            LOGGER.debug("Created bus {}", bus.getId());
        }
        return result;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveBuses(List<Bus> buses) {
        try {
            busRepository.saveAll(buses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        LOGGER.debug("Saved {} buses", buses.size());
    }

    public void updateBuses(Bus bus) {
        try {
            busRepository.findById(bus.getId())
                    .orElseThrow(() -> new IllegalStateException("Bus with id " + bus.getId() + " not found"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        busRepository.update(bus);
        LOGGER.debug("Updated bus {}", bus.getId());
    }

    public boolean deleteBuses(Bus bus) {
        try {
            busRepository.findById(bus.getId())
                    .orElseThrow(() -> new IllegalStateException("Bus with id " + bus.getId() + " not found"));
            busRepository.delete(bus.getId());
            LOGGER.debug("Deleted bus {}", bus.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void printAll() {
        for (Bus bus : busRepository.getAll()) {
            System.out.println(bus);
        }
    }
}
