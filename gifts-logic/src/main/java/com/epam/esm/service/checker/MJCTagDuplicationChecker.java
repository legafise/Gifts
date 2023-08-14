package com.epam.esm.service.checker;

import com.epam.esm.entity.Tag;

public interface MJCTagDuplicationChecker {
    boolean checkTagForDuplication(Tag tag);
}
