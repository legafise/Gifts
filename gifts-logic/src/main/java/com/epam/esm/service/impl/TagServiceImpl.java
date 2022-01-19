package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.checker.TagUpdateChecker;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private static final String NONEXISTENT_TAG_MESSAGE = "Nonexistent tag";
    private static final String INVALID_TAG_MESSAGE = "Invalid tag";
    private final TagValidator tagValidator;
    private final TagUpdateChecker tagUpdateChecker;
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagValidator tagValidator, TagUpdateChecker tagUpdateChecker, TagDao tagDao) {
        this.tagValidator = tagValidator;
        this.tagUpdateChecker = tagUpdateChecker;
        this.tagDao = tagDao;
    }

    @Override
    public boolean addTag(Tag tag) {
        if (tagValidator.validateTag(tag)) {
            return tagDao.add(tag);
        }

        throw new ServiceException(INVALID_TAG_MESSAGE);
    }

    @Override
    public List<Tag> findAllTags() {
        return tagDao.findAll();
    }

    @Override
    public Tag findTagById(String id) {
        Optional<Tag> tag = tagDao.findById(Long.parseLong(id));
        if (tag.isPresent()) {
            return tag.get();
        }

        throw new ServiceException(NONEXISTENT_TAG_MESSAGE);
    }

    @Override
    public boolean updateTag(Tag tag) {
        if (tagValidator.validateTag(tag) && tagUpdateChecker.isOriginalTag(tag)) {
            return tagDao.update(tag);
        }

        throw new ServiceException(INVALID_TAG_MESSAGE);
    }

    @Override
    public boolean removeTagById(String id) {
        return tagDao.remove(Long.parseLong(id));
    }

    @Override
    public boolean patchTag(Tag tag) {
        if (tagValidator.validateTag(tag)) {
            return tagDao.update(tag);
        }

        throw new ServiceException(INVALID_TAG_MESSAGE);
    }
}
