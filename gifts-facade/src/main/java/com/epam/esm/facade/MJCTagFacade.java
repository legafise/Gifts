package com.epam.esm.facade;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Map;

public interface MJCTagFacade {
    TagDto addTag(Tag tag);

    List<TagDto> findAllTags(Map<String, String> paginationParameters);

    TagDto findTagById(long id);

    TagDto findTagByName(String name);

    TagDto updateTag(Tag tag);

    void removeTagById(long id);

    void addTagIfNotExists(Tag tag);

    TagDto findWidelyUsedTag();
}
