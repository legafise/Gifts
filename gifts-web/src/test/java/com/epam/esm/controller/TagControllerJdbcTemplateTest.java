package com.epam.esm.controller;

import com.epam.esm.MJCApplication;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.constant.MJCPaginationConstant;
import com.epam.esm.service.exception.MJCEntityDuplicationException;
import com.epam.esm.service.exception.MJCInvalidPaginationDataException;
import com.epam.esm.service.exception.MJCMissingPageNumberException;
import com.epam.esm.service.exception.MJCUnknownEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MJCApplication.class)
@ActiveProfiles("template-test")
@Transactional
class TagControllerJdbcTemplateTest {
    @Autowired
    private MJCTagController tagController;
    private TagDto testTagDto;
    private List<TagDto> testTagDtoList;

    @BeforeEach
    void setUp() {
        testTagDto = new TagDto(101, "Tattoo");
        TagDto secondTestTag = new TagDto(102, "Jumps");
        testTagDtoList = Arrays.asList(testTagDto, secondTestTag);
    }

    @Test
    void readAllTagsTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");

        Assertions.assertEquals(testTagDtoList, tagController.readAllTags(pageParameters));
    }

    @Test
    void readAllTagsWithInvalidPageTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "-1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");

        Assertions.assertThrows(MJCInvalidPaginationDataException.class, () -> tagController.readAllTags(pageParameters));
    }

    @Test
    void readAllTagsWithInvalidPageSizeTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "-2");

        Assertions.assertThrows(MJCInvalidPaginationDataException.class, () -> tagController.readAllTags(pageParameters));
    }

    @Test
    void readAllTagsWithoutPaginationParametersTest() {
        Assertions.assertThrows(MJCMissingPageNumberException.class, () -> tagController.readAllTags(Collections.emptyMap()));
    }

    @Test
    void readWidelyUsedTagTest() {
        Assertions.assertEquals(testTagDto, tagController.readWidelyUsedTag());
    }

    @Test
    void readTagTest() {
        Assertions.assertEquals(testTagDto, tagController.readTag(101));
    }

    @Test
    void readTagWithInvalidIdTest() {
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> tagController.readTag(600));
    }

    @Test
    void updateTagPositiveTest() {
        testTagDto.setName("SuperTattoo");
        Assertions.assertEquals(testTagDto, tagController.updateTag(new Tag("SuperTattoo"), 101));
    }

    @Test
    void updateTagToExistedTagTest() {
        Assertions.assertThrows(MJCEntityDuplicationException.class, () -> tagController.updateTag(new Tag("Jumps"), 101));
    }

    @Test
    void deleteTagTest() {
        tagController.deleteTag(104);
    }

    @Test
    void deleteTagWithInvalidIdTest() {
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> tagController.deleteTag(600));
    }
}