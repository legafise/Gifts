package com.epam.esm.service.checker;

import com.epam.esm.entity.Tag;

public interface TagUpdateChecker {
    boolean isOriginalTag(Tag tag);
}
