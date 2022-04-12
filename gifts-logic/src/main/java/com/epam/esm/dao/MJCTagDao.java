package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MJCTagDao {
    void add(Tag tag);

    Optional<Tag> findById(long id);

    Optional<Tag> findByName(String name);

    List<Tag> findAll(int page, int pageSize);

    Tag update(Tag tag);

    void remove(long id);

    void removeTagFromCertificates(long id);

    Tag findWidelyUsedTag();

    List<Tag> findTagsByCertificateId(long certificateId);
}