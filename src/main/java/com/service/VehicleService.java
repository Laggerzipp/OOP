package com.service;

import com.model.Manufacturer;
import com.model.Vehicle;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class VehicleService<T extends Vehicle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    protected static final Random RANDOM = new Random();
    protected final CrudRepository<T> repository;

    public VehicleService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> createAndSave(int count) {
        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T vehicle = create();
            result.add(vehicle);
            try {
                repository.save(vehicle);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            LOGGER.debug("Created vehicle {}", vehicle.getId());
        }
        return result;
    }

    protected Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    protected abstract T create();

    public void saveAll(List<T> vehicles) {
        try {
            repository.saveAll(vehicles);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        LOGGER.debug("Saved {} vehicles", vehicles.size());
    }

    public void save(T vehicle) {
        try {
            repository.save(vehicle);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        LOGGER.debug("Saved {} vehicle", vehicle.getId());
    }

    public boolean update(T vehicle) {
        try {
            repository.findById(vehicle.getId())
                    .orElseThrow(() -> new IllegalStateException("Bus with id " + vehicle.getId() + " not found"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        repository.update(vehicle);
        LOGGER.debug("Updated vehicle {}", vehicle.getId());
        return true;
    }

    public boolean delete(T vehicle) {
        try {
            repository.findById(vehicle.getId())
                    .orElseThrow(() -> new IllegalStateException("Vehicle with id " + vehicle.getId() + " not found"));
            repository.delete(vehicle.getId());
            LOGGER.debug("Deleted bus {}", vehicle.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void printAll() {
        for (T vehicle : repository.getAll()) {
            System.out.println(vehicle);
        }
    }
}
