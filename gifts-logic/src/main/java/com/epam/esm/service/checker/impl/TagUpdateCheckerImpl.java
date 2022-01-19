package com.epam.esm.service.checker.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.checker.TagUpdateChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TagUpdateCheckerImpl implements TagUpdateChecker {
    private final TagDao tagDao;

    @Autowired
    public TagUpdateCheckerImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public boolean isOriginalTag(Tag tag) {
        Optional<Tag> updatingTagOptional = tagDao.findById(tag.getId());
        if (updatingTagOptional.isPresent()) {
            Tag updatingTag = updatingTagOptional.get();
            return !updatingTag.getName().equals(tag.getName());
        }

        return false;
    }
}
