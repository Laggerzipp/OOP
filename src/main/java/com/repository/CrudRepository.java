package com.repository;

import com.model.Auto;

import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean save(T thing);

    boolean saveAll(List<T> thing);

    boolean update(T thing);

    boolean delete(String id);
}
