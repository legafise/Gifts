package com.epam.esm.facade.impl;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.MJCTagService;
import com.epam.esm.service.constant.MJCPaginationConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class MJCTagFacadeImplTest {
    private MJCTagFacadeImpl tagFacade;
    private MJCTagService tagService;
    private MJCConverter<Tag, TagDto> tagConverter;
    private Tag testTag;
    private TagDto testTagDto;
    private List<Tag> tagList;
    private List<TagDto> tagDtoList;

    @BeforeEach
    void setUp() {
        tagFacade = new MJCTagFacadeImpl();
        tagConverter = (MJCConverter<Tag, TagDto>) mock(MJCConverter.class);
        tagService = mock(MJCTagService.class);
        tagFacade.setTagConverter(tagConverter);
        tagFacade.setTagService(tagService);

        testTag = new Tag(1, "Tattoo");
        Tag secondTestTag = new Tag(2, "Jumps");
        testTagDto = new TagDto(1, "Tattoo");
        TagDto secondTestTagDto = new TagDto(2, "Jumps");

        tagList = Arrays.asList(testTag, secondTestTag);
        tagDtoList = Arrays.asList(testTagDto, secondTestTagDto);
    }

    @Test
    void addTagTest() {
        when(tagService.addTag(testTag)).thenReturn(testTag);
        when(tagConverter.convert(testTag)).thenReturn(testTagDto);
        Assertions.assertEquals(testTagDto, tagFacade.addTag(testTag));
    }

    @Test
    void findAllTagsTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");

        when(tagService.findAllTags(pageParameters)).thenReturn(tagList);
        when(tagConverter.convertAll(tagList)).thenReturn(tagDtoList);
        Assertions.assertEquals(tagDtoList, tagFacade.findAllTags(pageParameters));
    }

    @Test
    void findTagByIdTest() {
        when(tagService.findTagById(1)).thenReturn(testTag);
        when(tagConverter.convert(testTag)).thenReturn(testTagDto);
        Assertions.assertEquals(tagFacade.findTagById(1), testTagDto);
    }

    @Test
    void findTagByNameTest() {
        when(tagService.findTagByName("Tattoo")).thenReturn(testTag);
        when(tagConverter.convert(testTag)).thenReturn(testTagDto);
        Assertions.assertEquals(tagFacade.findTagByName("Tattoo"), testTagDto);
    }

    @Test
    void removeTagByIdTest() {
        doNothing().when(tagService).removeTagById(1);
        tagFacade.removeTagById(1);
    }

    @Test
    void updateTagTest() {
        testTag.setName("UpdatedTag");
        testTagDto.setName("UpdatedTag");
        when(tagService.updateTag(testTag)).thenReturn(testTag);
        when(tagConverter.convert(testTag)).thenReturn(testTagDto);
        Assertions.assertEquals(testTagDto, tagFacade.updateTag(testTag));
    }

    @Test
    void findWidelyUsedTagTest() {
        when(tagService.findWidelyUsedTag()).thenReturn(testTag);
        Assertions.assertEquals(testTag, tagService.findWidelyUsedTag());
    }

    @Test
    void addTagIfNotExistsTest() {
        doNothing().when(tagService).addTagIfNotExists(testTag);
        tagFacade.addTagIfNotExists(testTag);
    }
}