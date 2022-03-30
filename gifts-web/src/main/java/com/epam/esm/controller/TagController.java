package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.facade.MJCTagFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final MJCTagFacade tagFacade;

    @Autowired
    public TagController(MJCTagFacade tagFacade) {
        this.tagFacade = tagFacade;
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<TagDto> readAllTags(@RequestParam Map<String, String> paginationParameters) {
        return tagFacade.findAllTags(paginationParameters);
    }

    @GetMapping("/widely-used-tag")
    @ResponseStatus(OK)
    public TagDto readWidelyUsedTag() {
        return tagFacade.findWidelyUsedTag();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TagDto createTag(@RequestBody Tag tag) {
        return tagFacade.addTag(tag);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public TagDto readTag(@PathVariable long id) {
        return tagFacade.findTagById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public TagDto updateTag(@RequestBody Tag tag, @PathVariable long id) {
        tag.setId(id);
        return tagFacade.updateTag(tag);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagFacade.removeTagById(id);
    }
}
