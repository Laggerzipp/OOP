package com.repository;

import com.model.Auto;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AutoRepository implements CrudRepository<Auto> {
    private final List<Auto> autos;

    public AutoRepository() {
        autos = new LinkedList<>();
    }

    @Override
    public Optional<Auto> findById(String id) {
        for (Auto auto : autos) {
            if (auto.getId().equals(id)) {
                return Optional.of(auto);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Auto> getAll() {
        return autos;
    }

    @Override
    public boolean save(Auto auto) {
        if (auto == null) {
            throw new IllegalStateException("Cant save auto if it are null");
        }
        autos.add(auto);
        return true;
    }

    @Override
    public boolean saveAll(List<Auto> auto) {
        if (auto == null) {
            throw new IllegalStateException("Cant save autos if they are null");
        }
        return autos.addAll(auto);
    }

    @Override
    public boolean update(Auto auto) {
        final Optional<Auto> founded = findById(auto.getId());
        if (founded.isPresent()) {
            AutoCopy.copy(auto, founded.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Auto> iterator = autos.iterator();
        while (iterator.hasNext()) {
            final Auto auto = iterator.next();
            if (auto.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class AutoCopy {
        static void copy(final Auto from, final Auto to) {
            to.setManufacturer(from.getManufacturer());
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
        }
    }
}
