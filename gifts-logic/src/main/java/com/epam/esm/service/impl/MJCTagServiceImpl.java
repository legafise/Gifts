package com.epam.esm.service.impl;

import com.epam.esm.dao.MJCTagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.MJCTagService;
import com.epam.esm.service.checker.MJCTagDuplicationChecker;
import com.epam.esm.service.constant.MJCPaginationConstant;
import com.epam.esm.service.exception.MJCEntityDuplicationException;
import com.epam.esm.service.exception.MJCInvalidEntityException;
import com.epam.esm.service.exception.MJCUnknownEntityException;
import com.epam.esm.service.handler.MJCPaginationParametersHandler;
import com.epam.esm.service.validator.MJCTagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MJCTagServiceImpl implements MJCTagService {
    private static final String NONEXISTENT_TAG_MESSAGE = "nonexistent.tag";
    private static final String REPEATING_TAG_MESSAGE = "repeating.tag";
    private static final String INVALID_TAG_MESSAGE = "invalid.tag";
    private final MJCTagValidator tagValidator;
    private final MJCTagDuplicationChecker tagDuplicationChecker;
    private final MJCTagDao tagDao;
    private final MJCPaginationParametersHandler paginationParametersHandler;

    @Autowired
    public MJCTagServiceImpl(MJCTagValidator tagValidator, MJCTagDuplicationChecker tagDuplicationChecker,
                             MJCTagDao tagDao, MJCPaginationParametersHandler paginationParametersHandler) {
        this.tagValidator = tagValidator;
        this.tagDuplicationChecker = tagDuplicationChecker;
        this.tagDao = tagDao;
        this.paginationParametersHandler = paginationParametersHandler;
    }

    @Override
    @Transactional
    public Tag addTag(Tag tag) {
        if (!tagValidator.validateTag(tag)) {
            throw new MJCInvalidEntityException(Tag.class, INVALID_TAG_MESSAGE);
        }

        if (!tagDuplicationChecker.checkTagForDuplication(tag)) {
            throw new MJCEntityDuplicationException(Tag.class, REPEATING_TAG_MESSAGE);
        }

        tagDao.add(tag);
        return findTagByName(tag.getName());
    }

    @Override
    public List<Tag> findAllTags(Map<String, String> paginationParameters) {
        Map<String, Integer> handledPaginationParameters = paginationParametersHandler.handlePaginationParameters(paginationParameters);

        return tagDao.findAll(handledPaginationParameters.get(MJCPaginationConstant.PAGE_PARAMETER),
                handledPaginationParameters.get(MJCPaginationConstant.PAGE_SIZE_PARAMETER));
    }

    @Override
    public Tag findTagById(long id) {
        Optional<Tag> tag = tagDao.findById(id);
        if (!tag.isPresent()) {
            throw new MJCUnknownEntityException(Tag.class, NONEXISTENT_TAG_MESSAGE);
        }

        return tag.get();
    }

    @Override
    public Tag findTagByName(String name) {
        Optional<Tag> tag = tagDao.findByName(name);
        if (!tag.isPresent()) {
            throw new MJCUnknownEntityException(Tag.class, NONEXISTENT_TAG_MESSAGE);
        }

        return tag.get();
    }

    @Override
    public Tag updateTag(Tag tag) {
        if (!tagValidator.validateTag(tag)) {
            throw new MJCInvalidEntityException(Tag.class, INVALID_TAG_MESSAGE);
        }

        if (!tagDuplicationChecker.checkTagForDuplication(tag)) {
            throw new MJCEntityDuplicationException(Tag.class, REPEATING_TAG_MESSAGE);
        }

        tagDao.update(tag);
        return findTagById(tag.getId());
    }

    @Override
    @Transactional
    public void removeTagById(long id) {
        if (!tagDao.findById(id).isPresent()) {
            throw new MJCUnknownEntityException(Tag.class, NONEXISTENT_TAG_MESSAGE);
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

    @Override
    public Tag findWidelyUsedTag() {
        return tagDao.findWidelyUsedTag();
    }
}
