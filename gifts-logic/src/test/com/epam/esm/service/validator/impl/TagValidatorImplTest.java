package com.epam.esm.service.validator.impl;

import com.epam.esm.entity.Tag;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagValidatorImplTest {
    private TagValidatorImpl tagValidator;
    private Tag testTag;

    @BeforeEach
    void setUp() {
        tagValidator = new TagValidatorImpl();
        testTag = new Tag(1, "Jumps");
    }

    @Test
    void validateTagPositiveTest() {
        Assert.assertTrue(tagValidator.validateTag(testTag));
    }

    @Test
    void validateTagWithInvalidNameTest() {
        testTag.setName("d");
        Assert.assertFalse(tagValidator.validateTag(testTag));
    }

    @Test
    void validateTagWithNullNameTest() {
        testTag.setName(null);
        Assert.assertFalse(tagValidator.validateTag(testTag));
    }
}