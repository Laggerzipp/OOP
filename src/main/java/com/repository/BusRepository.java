package com.repository;

import com.model.vehicle.Bus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BusRepository implements CrudRepository<Bus> {
    private final List<Bus> buses;

    public BusRepository() {
        buses = new LinkedList<>();
    }

    @Override
    public Optional<Bus> findById(String id) {
        for (Bus bus : buses) {
            if (bus.getId().equals(id)) {
                return Optional.of(bus);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Bus> getAll() {
        return buses;
    }

    @Override
    public boolean save(Bus bus) {
        if (bus == null) {
            throw new IllegalStateException("Cant save bus if it are null");
        }
        buses.add(bus);
        return true;

    }

    @Override
    public boolean saveAll(List<Bus> bus) {
        if (bus == null) {
            throw new IllegalStateException("Cant save buses if they are null");
        }
        return buses.addAll(bus);

    }

    @Override
    public boolean update(Bus bus) {
        final Optional<Bus> founded = findById(bus.getId());
        if (founded.isPresent()) {
            BusCopy.copy(bus, founded.get());
            return true;
        }
        return false;
    }


    @Override
    public boolean delete(String id) {
        Iterator<Bus> iterator = buses.iterator();
        while (iterator.hasNext()) {
            final Bus bus = iterator.next();
            if (bus.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class BusCopy {
        static void copy(final Bus from, final Bus to) {
            to.setManufacturer(from.getManufacturer());
            to.setModel(from.getModel());
            to.setLineName(from.getLineName());
            to.setPrice(from.getPrice());
        }
    }
}
