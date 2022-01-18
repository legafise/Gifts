package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    boolean add(Tag tag);

    Optional<Tag> findById(Long id);

    List<Tag> findAll();

    boolean update(Tag certificate);

    boolean remove(Long id);
}
