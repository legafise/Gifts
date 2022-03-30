package com.epam.esm.service.checker.impl;

import com.epam.esm.dao.MJCTagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.checker.MJCTagDuplicationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MJCTagDuplicationCheckerImpl implements MJCTagDuplicationChecker {
    private final MJCTagDao tagDao;

    @Autowired
    public MJCTagDuplicationCheckerImpl(MJCTagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public boolean checkTagForDuplication(Tag tag) {
        Optional<Tag> duplicationTag = tagDao.findByName(tag.getName());

        return !duplicationTag.isPresent() || duplicationTag.get().getId() == tag.getId();
    }
}
