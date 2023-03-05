package com.repository;

import com.model.Auto;
import com.model.Sportcar;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SportcarRepository implements CrudRepository<Sportcar> {
    private final List<Sportcar> sportcars;

    public SportcarRepository() {
        this.sportcars = new LinkedList<Sportcar>();
    }

    @Override
    public Sportcar getById(String id) {
        for (Sportcar car : sportcars) {
            if (car.getId().equals(id))
                return car;
        }
        return null;
    }

    @Override
    public List<Sportcar> getAll() {
        return sportcars;
    }

    @Override
    public boolean save(Sportcar car) {
        sportcars.add(car);
        return true;
    }

    @Override
    public boolean saveAll(List<Sportcar> car) {
        if (car == null) {
            return false;
        }
        return sportcars.addAll(car);
    }

    @Override
    public boolean update(Sportcar car) {
        final Sportcar founded = getById(car.getId());
        if (founded != null) {
            SportcarCopy.copy(founded, car);
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
