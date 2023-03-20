package com.repository;

import com.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<O extends Vehicle> {
    Optional<O> findById(String id);

    List<O> getAll();

    boolean save(O thing);

    boolean saveAll(List<O> thing);

    boolean update(O thing);

    boolean delete(String id);
}
