package com.epam.esm.dao;

import com.epam.esm.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends Entity> {
    boolean add(T entity);

    Optional<T> findById(K id);

    List<T> findAll();

    boolean update(T entity);

    boolean remove(K id);
}
