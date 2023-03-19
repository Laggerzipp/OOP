package com.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T thing);

    boolean saveAll(List<T> thing);

    boolean update(T thing);

    boolean delete(String id);
}
