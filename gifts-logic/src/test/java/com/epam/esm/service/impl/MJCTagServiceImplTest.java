package com.epam.esm.service.impl;

import com.epam.esm.dao.MJCTagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.checker.MJCTagDuplicationChecker;
import com.epam.esm.service.constant.MJCPaginationConstant;
import com.epam.esm.service.exception.MJCEntityDuplicationException;
import com.epam.esm.service.exception.MJCInvalidEntityException;
import com.epam.esm.service.exception.MJCUnknownEntityException;
import com.epam.esm.service.handler.MJCPaginationParametersHandler;
import com.epam.esm.service.validator.MJCTagValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MJCTagServiceImplTest {
    private MJCTagServiceImpl tagService;
    private MJCTagValidator tagValidator;
    private MJCTagDuplicationChecker tagDuplicationChecker;
    private MJCPaginationParametersHandler paginationParametersHandler;
    private MJCTagDao tagDao;
    private Tag firstTestTag;
    private List<Tag> tags;

    @BeforeEach
    void setUp() {
        tagValidator = mock(MJCTagValidator.class);
        tagDuplicationChecker = mock(MJCTagDuplicationChecker.class);
        tagDao = mock(MJCTagDao.class);
        paginationParametersHandler = mock(MJCPaginationParametersHandler.class);
        tagService = new MJCTagServiceImpl(tagValidator, tagDuplicationChecker, tagDao, paginationParametersHandler);

        firstTestTag = new Tag(1, "Jumps");
        Tag secondTestTag = new Tag(2, "Fly");
        tags = Arrays.asList(firstTestTag, secondTestTag);
    }

    @Test
    void addTagPositiveTest() {
        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
        Assertions.assertEquals(tagService.addTag(firstTestTag), firstTestTag);
    }

    @Test
    void addTagWithInvalidTagTest() {
        when(tagValidator.validateTag(firstTestTag)).thenReturn(false);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> tagService.addTag(firstTestTag));
    }

    @Test
    void addDuplicateTagTest() {
        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(false);
        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
        Assertions.assertThrows(MJCEntityDuplicationException.class, () -> tagService.addTag(firstTestTag));
    }

    @Test
    void findAllTagsTest() {
        Map<String, String> paginationParameters = new HashMap<>();
        paginationParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        paginationParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");

        Map<String, Integer> handledPaginationParameters = new HashMap<>();
        handledPaginationParameters.put(MJCPaginationConstant.PAGE_PARAMETER, 1);
        handledPaginationParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, 2);

        when(tagDao.findAll(1,2)).thenReturn(tags);
        when(paginationParametersHandler.handlePaginationParameters(paginationParameters)).thenReturn(handledPaginationParameters);
        Assertions.assertEquals(tagService.findAllTags(paginationParameters), tags);
    }

    @Test
    void findTagByIdTest() {
        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
        Assertions.assertEquals(tagService.findTagById(1), firstTestTag);
    }

    @Test
    void findTagWithInvalidIdTest() {
        when(tagDao.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> tagService.findTagById(1));
    }

    @Test
    void findTagByNameTest() {
        when(tagDao.findByName("Jumps")).thenReturn(Optional.of(firstTestTag));
        Assertions.assertEquals(tagService.findTagByName("Jumps"), firstTestTag);
    }

    @Test
    void findTagWithInvalidNameTest() {
        when(tagDao.findByName("Jumps")).thenReturn(Optional.empty());
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> tagService.findTagByName("Jumps"));
    }

    @Test
    void removeTagWithInvalidIdTest() {
        when(tagDao.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(MJCUnknownEntityException.class ,() -> tagService.removeTagById(1));
    }

    @Test
    void updateTagPositiveTest() {
        firstTestTag.setName("Test");
        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
        Assertions.assertEquals(tagService.updateTag(firstTestTag), firstTestTag);
    }

    @Test
    void updateTagWithInvalidTagTest() {
        firstTestTag.setName("");
        when(tagValidator.validateTag(firstTestTag)).thenReturn(false);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> tagService.updateTag(firstTestTag));
    }

    @Test
    void updateTagWithDuplicationTest() {
        firstTestTag.setName("Fly");
        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(false);
        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
        Assertions.assertThrows(MJCEntityDuplicationException.class, () -> tagService.updateTag(firstTestTag));
    }
}