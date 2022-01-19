package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    boolean addTag(Tag tag);
    List<Tag> findAllTags();
    Tag findTagById(String id);
    boolean updateTag(Tag tag);
    boolean removeTagById(String id);
    boolean patchTag(Tag tag);
}
