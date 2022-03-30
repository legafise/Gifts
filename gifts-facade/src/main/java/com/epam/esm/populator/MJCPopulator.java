package com.epam.esm.populator;

public interface MJCPopulator<SOURCE, TARGET> {
    void populate(SOURCE source, TARGET target);
}
