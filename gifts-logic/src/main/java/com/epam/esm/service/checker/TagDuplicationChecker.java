package com.epam.esm.service.checker;

import com.epam.esm.entity.Tag;

public interface TagDuplicationChecker {
    boolean checkTagForDuplication(Tag tag);
}
