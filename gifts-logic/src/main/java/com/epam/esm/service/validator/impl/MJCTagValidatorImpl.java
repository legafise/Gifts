package com.epam.esm.service.validator.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.validator.MJCTagValidator;
import org.springframework.stereotype.Component;

@Component
public class MJCTagValidatorImpl implements MJCTagValidator {
    @Override
    public boolean validateTag(Tag tag) {
        return tag != null && validateTagName(tag.getName());
    }

    private boolean validateTagName(String tagName) {
        return tagName != null && tagName.length() > 1 && tagName.length() <= 50;
    }
}
