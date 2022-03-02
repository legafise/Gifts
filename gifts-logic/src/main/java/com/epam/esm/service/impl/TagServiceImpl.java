package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.checker.TagDuplicationChecker;
import com.epam.esm.service.exception.DuplicateTagException;
import com.epam.esm.service.exception.InvalidTagException;
import com.epam.esm.service.exception.UnknownTagException;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private static final String NONEXISTENT_TAG_MESSAGE = "nonexistent.tag";
    private static final String REPEATING_TAG_MESSAGE = "repeating.tag";
    private static final String INVALID_TAG_MESSAGE = "invalid.tag";
    private final TagValidator tagValidator;
    private final TagDuplicationChecker tagDuplicationChecker;
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagValidator tagValidator, TagDuplicationChecker tagDuplicationChecker, TagDao tagDao) {
        this.tagValidator = tagValidator;
        this.tagDuplicationChecker = tagDuplicationChecker;
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public Tag addTag(Tag tag) {
        if (!tagValidator.validateTag(tag)) {
            throw new InvalidTagException(INVALID_TAG_MESSAGE);
        }

        if (!tagDuplicationChecker.checkTagForDuplication(tag)) {
            throw new DuplicateTagException(REPEATING_TAG_MESSAGE);
        }

        tagDao.add(tag);
        return tagDao.findByName(tag.getName()).get();
    }

    @Override
    public List<Tag> findAllTags() {
        return tagDao.findAll();
    }

    @Override
    public Tag findTagById(long id) {
        Optional<Tag> tag = tagDao.findById(id);
        if (!tag.isPresent()) {
            throw new UnknownTagException(NONEXISTENT_TAG_MESSAGE);
        }

        return tag.get();
    }

    @Override
    public Tag findTagByName(String name) {
        Optional<Tag> tag = tagDao.findByName(name);
        if (!tag.isPresent()) {
            throw new UnknownTagException(NONEXISTENT_TAG_MESSAGE);
        }

        return tag.get();
    }

    @Override
    public Tag updateTag(Tag tag) {
        if (!tagValidator.validateTag(tag)) {
            throw new InvalidTagException(INVALID_TAG_MESSAGE);
        }

        if (!tagDuplicationChecker.checkTagForDuplication(tag)) {
            throw new DuplicateTagException(REPEATING_TAG_MESSAGE);
        }

        tagDao.update(tag);
        return findTagById(tag.getId());
    }

    @Override
    @Transactional
    public void removeTagById(long id) {
        if (!tagDao.findById(id).isPresent()) {
            throw new UnknownTagException(NONEXISTENT_TAG_MESSAGE);
        }

        tagDao.removeTagFromCertificates(id);
        tagDao.remove(id);
    }

    @Override
    @Transactional
    public void addTagIfNotExists(Tag tag) {
        Optional<Tag> searchedTag = tagDao.findByName(tag.getName());
        if (!searchedTag.isPresent()) {
            addTag(tag);
        }
    }
}
