package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.checker.TagDuplicationChecker;
import com.epam.esm.service.exception.DuplicateTagException;
import com.epam.esm.service.exception.InvalidCertificateException;
import com.epam.esm.service.exception.InvalidTagException;
import com.epam.esm.service.exception.UnknownTagException;
import com.epam.esm.service.validator.TagValidator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TagServiceImplTest {
    private TagServiceImpl tagService;
    private TagValidator tagValidator;
    private TagDuplicationChecker tagDuplicationChecker;
    private TagDao tagDao;
    private Tag firstTestTag;
    private List<Tag> tags;

    @BeforeEach
    void setUp() {
        tagValidator = mock(TagValidator.class);
        tagDuplicationChecker = mock(TagDuplicationChecker.class);
        tagDao = mock(TagDao.class);
        tagService = new TagServiceImpl(tagValidator, tagDuplicationChecker, tagDao);

        firstTestTag = new Tag(1, "Jumps");
        Tag secondTestTag = new Tag(2, "Fly");
        tags = Arrays.asList(firstTestTag, secondTestTag);
    }

    @Test
    void addTagPositiveTest() {
        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
        when(tagDao.add(firstTestTag)).thenReturn(true);
        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
        Assert.assertEquals(tagService.addTag(firstTestTag), firstTestTag);
    }

    @Test
    void addTagWithInvalidTagTest() {
        when(tagValidator.validateTag(firstTestTag)).thenReturn(false);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
        when(tagDao.add(firstTestTag)).thenReturn(true);
        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
        Assert.assertThrows(InvalidTagException.class, () -> tagService.addTag(firstTestTag));
    }

    @Test
    void addDuplicateTagTest() {
        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(false);
        when(tagDao.add(firstTestTag)).thenReturn(true);
        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
        Assert.assertThrows(DuplicateTagException.class, () -> tagService.addTag(firstTestTag));
    }

    @Test
    void findAllTagsTest() {
        when(tagDao.findAll()).thenReturn(tags);
        Assert.assertEquals(tagService.findAllTags(), tags);
    }

    @Test
    void findTagByIdTest() {
        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
        Assert.assertEquals(tagService.findTagById(1), firstTestTag);
    }

    @Test
    void findTagWithInvalidIdTest() {
        when(tagDao.findById(1)).thenReturn(Optional.empty());
        Assert.assertThrows(UnknownTagException.class, () -> tagService.findTagById(1));
    }

    @Test
    void findTagByNameTest() {
        when(tagDao.findByName("Jumps")).thenReturn(Optional.of(firstTestTag));
        Assert.assertEquals(tagService.findTagByName("Jumps"), firstTestTag);
    }

    @Test
    void findTagWithInvalidNameTest() {
        when(tagDao.findByName("Jumps")).thenReturn(Optional.empty());
        Assert.assertThrows(UnknownTagException.class, () -> tagService.findTagByName("Jumps"));
    }

    @Test
    void removeTagByIdPositiveTest() {
        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
        when(tagDao.remove(1)).thenReturn(true);
        Assert.assertTrue(tagService.removeTagById(1));
    }

    @Test
    void removeTagWithInvalidIdTest() {
        when(tagDao.findById(1)).thenReturn(Optional.empty());
        Assert.assertThrows(UnknownTagException.class ,() -> tagService.removeTagById(1));
    }

    @Test
    void updateTagPositiveTest() {
        firstTestTag.setName("Test");
        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
        Assert.assertEquals(tagService.updateTag(firstTestTag), firstTestTag);
    }

    @Test
    void updateTagWithInvalidTagTest() {
        firstTestTag.setName("");
        when(tagValidator.validateTag(firstTestTag)).thenReturn(false);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(true);
        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
        Assert.assertThrows(InvalidTagException.class, () -> tagService.updateTag(firstTestTag));
    }

    @Test
    void updateTagWithDuplicationTest() {
        firstTestTag.setName("Fly");
        when(tagValidator.validateTag(firstTestTag)).thenReturn(true);
        when(tagDuplicationChecker.checkTagForDuplication(firstTestTag)).thenReturn(false);
        when(tagDao.findById(1)).thenReturn(Optional.of(firstTestTag));
        Assert.assertThrows(DuplicateTagException.class, () -> tagService.updateTag(firstTestTag));
    }
}