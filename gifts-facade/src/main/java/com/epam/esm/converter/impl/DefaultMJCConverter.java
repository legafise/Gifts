package com.epam.esm.converter.impl;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.populator.MJCPopulator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultMJCConverter<SOURCE, TARGET> implements MJCConverter<SOURCE, TARGET> {
    private final Class<TARGET> targetClass;
    private List<MJCPopulator<SOURCE, TARGET>> populators;

    public DefaultMJCConverter(Class<TARGET> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public TARGET convert(SOURCE source) {
        try {
            TARGET target = targetClass.newInstance();
            populators.forEach(populator -> populator.populate(source, target));
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<TARGET> convertAll(Collection<SOURCE> sources) {
        return sources.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public void setPopulators(List<MJCPopulator<SOURCE, TARGET>> populators) {
        this.populators = populators;
    }
}
