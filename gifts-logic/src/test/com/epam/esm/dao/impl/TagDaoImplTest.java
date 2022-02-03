package com.epam.esm.dao.impl;

import com.epam.esm.config.TestSpringConfig;
import com.epam.esm.entity.Tag;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestSpringConfig.class})
public class TagDaoImplTest {
    @Autowired
    private TagDaoImpl tagDao;
    private Tag firstTestTag;
    private Tag secondTestTag;
    private Tag thirdTestTag;
    private List<Tag> tagList;

    @BeforeEach
    void setUp() {
        firstTestTag = new Tag("Free");
        secondTestTag = new Tag(101, "Tattoo");
        thirdTestTag = new Tag(103, "Entertainment");
        tagList = Arrays.asList(secondTestTag, new Tag(102, "Jumps"), thirdTestTag, new Tag(104, "Swimming"));
    }

    @Test
    void findAllTagsTest() {
        Assert.assertEquals(tagList, tagDao.findAll());
    }

    @Test
    void addTagTest() {
        Assert.assertTrue(tagDao.add(firstTestTag));
    }

    @Test
    void findByIdTest() {
        Assert.assertEquals(tagDao.findById(103).get(), thirdTestTag);
    }

    @Test
    void findByIdWithInvalidIdTest() {
        Assert.assertFalse(tagDao.findById(300).isPresent());
    }

    @Test
    void findByNameTest() {
        Assert.assertEquals(tagDao.findByName("Tattoo").get(), secondTestTag);
    }

    @Test
    void findByIdWithInvalidNameTest() {
        Assert.assertFalse(tagDao.findByName("Banana").isPresent());
    }

    @Test
    void updateTagTest() {
        secondTestTag.setName("TattooLand");
        Assert.assertTrue(tagDao.update(secondTestTag));
    }

    @Test
    void removeTagPositiveTest() {
        Assert.assertTrue(tagDao.remove(1));
    }

    @Test
    void removeUnknownTagTest() {
        Assert.assertFalse(tagDao.remove(400));
    }
}