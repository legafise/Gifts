package com.epam.esm.populator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class MJCTagPopulator implements MJCPopulator<Tag, TagDto> {
    @Override
    public void populate(Tag tag, TagDto tagDto) {
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
    }
}
