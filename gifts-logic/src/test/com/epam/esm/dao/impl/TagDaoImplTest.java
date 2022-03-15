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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestApplication.class)
//@ActiveProfiles("test")
//class TagDaoImplTest {
//    @Autowired
//    private TagDaoImpl tagDao;
//    private Tag firstTestTag;
//    private Tag secondTestTag;
//    private Tag thirdTestTag;
//    private List<Tag> tagList;
//
//    @BeforeEach
//    void setUp() {
//        firstTestTag = new Tag(104,"Free");
//        secondTestTag = new Tag(101, "Tattoo");
//        thirdTestTag = new Tag(103, "Entertainment");
//        tagList = Arrays.asList(secondTestTag, new Tag(102, "Jumps"), thirdTestTag, new Tag(104, "Swimming"));
//    }
//
//    @Test
//    void findAllTagsTest() {
//        Assertions.assertEquals(tagList, tagDao.findAll(1, 4));
//    }
//
//    @Test
//    void addTagTest() {
//        tagDao.add(firstTestTag);
//        Assertions.assertTrue(tagDao.findById(firstTestTag.getId()).isPresent());
//    }
//
//    @Test
//    void findByIdTest() {
//        Assertions.assertEquals(tagDao.findById(103).get(), thirdTestTag);
//    }
//
//    @Test
//    void findByIdWithInvalidIdTest() {
//        Assertions.assertFalse(tagDao.findById(300).isPresent());
//    }
//
//    @Test
//    void findByNameTest() {
//        Assertions.assertEquals(tagDao.findByName("Tattoo").get(), secondTestTag);
//    }
//
//    @Test
//    void findByIdWithInvalidNameTest() {
//        Assertions.assertFalse(tagDao.findByName("Banana").isPresent());
//    }
//
//    @Test
//    void updateTagTest() {
//        secondTestTag.setName("TattooLand");
//        Assertions.assertTrue(tagDao.update(secondTestTag));
//    }
//
//    @Test
//    void removeTagPositiveTest() {
//        Assertions.assertTrue(tagDao.remove(1));
//    }
//
//    @Test
//    void removeUnknownTagTest() {
//        Assertions.assertFalse(tagDao.remove(400));
//    }
//}