package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    Tag addTag(Tag tag);

    List<Tag> findAllTags();

    Tag findTagById(String id);

    Tag findTagByName(String name);

    Tag updateTag(Tag tag, String id);

    boolean removeTagById(String id);

    void addTagIfNotExists(Tag tag);
}
