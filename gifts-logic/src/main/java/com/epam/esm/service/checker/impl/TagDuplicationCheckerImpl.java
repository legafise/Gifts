package com.epam.esm.service.checker.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.checker.TagDuplicationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagDuplicationCheckerImpl implements TagDuplicationChecker {
    private final TagDao tagDao;

    @Autowired
    public TagDuplicationCheckerImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public boolean checkTagForDuplication(Tag tag) {
        return !tagDao.findByName(tag.getName()).isPresent();
    }
}
