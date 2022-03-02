package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    Tag addTag(Tag tag);

    List<Tag> findAllTags();

    Tag findTagById(long id);

    Tag findTagByName(String name);

    Tag updateTag(Tag tag);

    void removeTagById(long id);

    void addTagIfNotExists(Tag tag);
}
