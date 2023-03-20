package com.repository;

import com.model.Sportcar;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SportcarRepository implements CrudRepository<Sportcar> {
    private final List<Sportcar> sportcars;

    public SportcarRepository() {
        this.sportcars = new LinkedList<>();
    }

    @Override
    public Optional<Sportcar> findById(String id) {
        for (Sportcar car : sportcars) {
            if (car.getId().equals(id))
                return Optional.of(car);
        }
        return Optional.empty();
    }

    @Override
    public List<Sportcar> getAll() {
        return sportcars;
    }

    @Override
    public boolean save(Sportcar car) {
        if (car == null) {
            throw new IllegalStateException("Cant save sport car if it are null");
        }
        sportcars.add(car);
        return true;

    }

    @Override
    public boolean saveAll(List<Sportcar> car) {
        if (car == null) {
            throw new IllegalStateException("Cant save sport cars if it are null");
        }
        return sportcars.addAll(car);
    }

    @Override
    public boolean update(Sportcar car) {
        final Optional<Sportcar> founded = findById(car.getId());
        if (founded.isPresent()) {
            SportcarCopy.copy(car, founded.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        Iterator<Sportcar> iterator = sportcars.iterator();
        while (iterator.hasNext()) {
            final Sportcar car = iterator.next();
            if (car.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class SportcarCopy {
        static void copy(final Sportcar from, final Sportcar to) {
            to.setManufacturer(from.getManufacturer());
            to.setModel(from.getModel());
            to.setMaxSpeed(from.getMaxSpeed());
            to.setPrice(from.getPrice());
        }
    }
}
