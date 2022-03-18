package com.epam.esm.dao.impl;

import com.epam.esm.TestApplication;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("template-test")
@Transactional
class JdbcTemplateTagDaoTest {
    @Autowired
    private JdbcTemplateTagDao tagDao;
    private Tag firstTestTag;
    private Tag secondTestTag;
    private Tag thirdTestTag;
    private List<Tag> tagList;

    @BeforeEach
    void setUp() {
        firstTestTag = new Tag(105, "Free");
        secondTestTag = new Tag(101, "Tattoo");
        thirdTestTag = new Tag(103, "Entertainment");
        tagList = Arrays.asList(secondTestTag, new Tag(102, "Jumps"), thirdTestTag, new Tag(104, "Swimming"));
    }

    @Test
    void findAllTagsTest() {
        Assertions.assertEquals(tagList, tagDao.findAll(1, 4));
    }

    @Test
    void findByIdTest() {
        Assertions.assertEquals(tagDao.findById(103).get(), thirdTestTag);
    }

    @Test
    void addTagTest() {
        tagDao.add(firstTestTag);
        Assertions.assertTrue(tagDao.findByName(firstTestTag.getName()).isPresent());
    }

    @Test
    void findWithInvalidIdTest() {
        Assertions.assertFalse(tagDao.findById(300).isPresent());
    }

    @Test
    void findByNameTest() {
        Assertions.assertEquals(tagDao.findByName("Entertainment").get(), thirdTestTag);
    }

    @Test
    void findWithInvalidNameTest() {
        Assertions.assertFalse(tagDao.findByName("Banana").isPresent());
    }

    @Test
    void updateTagTest() {
        secondTestTag.setName("TattooLand");
        Assertions.assertEquals(secondTestTag, tagDao.update(secondTestTag));
    }

    @Test
    void removeTagPositiveTest() {
        tagDao.add(firstTestTag);
        long addedTagId = tagDao.findByName(firstTestTag.getName()).get().getId();
        tagDao.remove(addedTagId);
        Assertions.assertFalse(tagDao.findById(addedTagId).isPresent());
    }
}