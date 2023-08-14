package com.epam.esm.controller.localizer.impl;

import com.epam.esm.controller.localizer.MJCLocalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MJCLocalizerImpl implements MJCLocalizer {
    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public MJCLocalizerImpl(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String toLocale(String messageCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageCode, null, locale);
    }
}
