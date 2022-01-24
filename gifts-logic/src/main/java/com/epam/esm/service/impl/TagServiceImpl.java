package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private static final String NONEXISTENT_TAG_MESSAGE = "Nonexistent tag";
    private static final String INVALID_TAG_MESSAGE = "Invalid tag";
    private final TagValidator tagValidator;
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagValidator tagValidator, TagDao tagDao) {
        this.tagValidator = tagValidator;
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public Tag addTag(Tag tag) {
        if (tagValidator.validateTag(tag) && tagDao.add(tag)) {
            return tagDao.findByName(tag.getName()).get();
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
    public Tag findTagByName(String name) {
        Optional<Tag> tag = tagDao.findByName(name);
        if (tag.isPresent()) {
            return tag.get();
        }

        throw new ServiceException(NONEXISTENT_TAG_MESSAGE);
    }

    @Override
    public Tag updateTag(Tag tag, String id) {
        if (tagValidator.validateTag(tag) && tagDao.update(tag, Long.parseLong(id))) {
            return findTagById(id);
        }

        throw new ServiceException(INVALID_TAG_MESSAGE);
    }

    @Override
    public boolean removeTagById(String id) {
        return tagDao.remove(Long.parseLong(id));
    }

    @Override
    public void addTagIfNotExists(Tag tag) {
        Optional<Tag> searchedTag = tagDao.findByName(tag.getName());
        if (!searchedTag.isPresent()) {
            addTag(tag);
        }
    }
}
