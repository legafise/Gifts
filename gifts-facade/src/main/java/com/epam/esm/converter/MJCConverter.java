package com.epam.esm.converter;

import java.util.Collection;
import java.util.List;

public interface MJCConverter<SOURCE, TARGET> {
    TARGET convert(SOURCE source);
    List<TARGET> convertAll(Collection<SOURCE> sourceCollection);
    // TODO: 28.03.2022 mjc добавить везде
}
