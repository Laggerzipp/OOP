package com.repository;

import com.model.SportCar;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SportCarRepository implements CrudRepository<SportCar> {
    private final List<SportCar> sportCars;

    public SportCarRepository() {
        this.sportCars = new LinkedList<>();
    }

    @Override
    public Optional<SportCar> findById(String id) {
        for (SportCar car : sportCars) {
            if (car.getId().equals(id))
                return Optional.of(car);
        }
        return Optional.empty();
    }

    @Override
    public List<SportCar> getAll() {
        return sportCars;
    }

    @Override
    public boolean save(SportCar car) {
        if (car == null) {
            throw new IllegalStateException("Cant save sport car if it are null");
        }
        sportCars.add(car);
        return true;

    }

    @Override
    public boolean saveAll(List<SportCar> car) {
        if (car == null) {
            throw new IllegalStateException("Cant save sport cars if it are null");
        }
        return sportCars.addAll(car);
    }

    @Override
    public boolean update(SportCar car) {
        final Optional<SportCar> founded = findById(car.getId());
        if (founded.isPresent()) {
            SportCarCopy.copy(car, founded.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        Iterator<SportCar> iterator = sportCars.iterator();
        while (iterator.hasNext()) {
            final SportCar car = iterator.next();
            if (car.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class SportCarCopy {
        static void copy(final SportCar from, final SportCar to) {
            to.setManufacturer(from.getManufacturer());
            to.setModel(from.getModel());
            to.setMaxSpeed(from.getMaxSpeed());
            to.setPrice(from.getPrice());
        }
    }
}
