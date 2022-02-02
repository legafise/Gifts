package com.epam.esm.service.checker.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TagDuplicationCheckerImplTest {
    private TagDuplicationCheckerImpl tagDuplicationChecker;
    private Tag firstTestTag;
    private Tag secondTestTag;
    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        tagDao = mock(TagDao.class);
        tagDuplicationChecker = new TagDuplicationCheckerImpl(tagDao);
        firstTestTag = new Tag(1, "Test");
        secondTestTag = new Tag(2, "Test");
    }

    @Test
    void checkTagForDuplicationWithNewTest() {
        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.empty());
        Assert.assertTrue(tagDuplicationChecker.checkTagForDuplication(firstTestTag));
    }

    @Test
    void checkTagForDuplicationWhileUpdateTest() {
        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(firstTestTag));
        Assert.assertTrue(tagDuplicationChecker.checkTagForDuplication(firstTestTag));
    }

    @Test
    void checkTagForDuplicationBadTest() {
        when(tagDao.findByName(firstTestTag.getName())).thenReturn(Optional.of(secondTestTag));
        Assert.assertFalse(tagDuplicationChecker.checkTagForDuplication(firstTestTag));
    }
}