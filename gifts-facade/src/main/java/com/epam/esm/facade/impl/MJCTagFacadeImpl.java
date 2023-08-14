package com.epam.esm.facade.impl;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.facade.MJCTagFacade;
import com.epam.esm.service.MJCTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MJCTagFacadeImpl implements MJCTagFacade {
    @Autowired
    @Qualifier("mjcTagConverter")
    private MJCConverter<Tag, TagDto> tagConverter;
    @Autowired
    private MJCTagService tagService;

    public void setTagConverter(MJCConverter<Tag, TagDto> tagConverter) {
        this.tagConverter = tagConverter;
    }

    public void setTagService(MJCTagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public TagDto addTag(Tag tag) {
        return tagConverter.convert(tagService.addTag(tag));
    }

    @Override
    public List<TagDto> findAllTags(Map<String, String> paginationParameters) {
        return tagConverter.convertAll(tagService.findAllTags(paginationParameters));
    }

    @Override
    public TagDto findTagById(long id) {
        return tagConverter.convert(tagService.findTagById(id));
    }

    @Override
    public TagDto findTagByName(String name) {
        return tagConverter.convert(tagService.findTagByName(name));
    }

    @Override
    public TagDto updateTag(Tag tag) {
        return tagConverter.convert(tagService.updateTag(tag));
    }

    @Override
    public void removeTagById(long id) {
        tagService.removeTagById(id);
    }

    @Override
    public void addTagIfNotExists(Tag tag) {
        tagService.addTagIfNotExists(tag);
    }

    @Override
    public TagDto findWidelyUsedTag() {
        return tagConverter.convert(tagService.findWidelyUsedTag());
    }
}
