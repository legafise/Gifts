package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    boolean add(Tag tag);

    Optional<Tag> findById(long id);

    Optional<Tag> findByName(String name);

    List<Tag> findAll();

    boolean update(Tag certificate, long id);

    boolean remove(long id);
}
