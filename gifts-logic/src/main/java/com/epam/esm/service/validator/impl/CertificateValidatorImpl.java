package com.epam.esm.service.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.service.validator.CertificateValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CertificateValidatorImpl implements CertificateValidator {
    private static final BigDecimal MIN_PRICE = new BigDecimal("1");
    private static final BigDecimal MAX_PRICE = new BigDecimal("100000");
    private static final long MIN_DURATION = 7;
    private static final long MAX_DURATION = 365;

    @Override
    public boolean validateCertificate(Certificate certificate) {
        return certificate != null && validateName(certificate.getName())
                && validateDescription(certificate.getDescription()) && validatePrice(certificate.getPrice())
                && validateDuration(certificate.getDuration()) && certificate.getCreateDate() != null
                && certificate.getLastUpdateDate() != null && certificate.getTags() != null;
    }

    private boolean validateDuration(short duration) {
        return duration > MIN_DURATION && duration <= MAX_DURATION;
    }

    private boolean validateName(String certificateName) {
        return certificateName != null && !certificateName.isEmpty() && certificateName.length() > 1
                && certificateName.length() <= 100;
    }

    private boolean validateDescription(String certificateDescription) {
        return certificateDescription != null && !certificateDescription.isEmpty()
                && certificateDescription.length() >= 20 && certificateDescription.length() <= 500;
    }

    private boolean validatePrice(BigDecimal price) {
        return price != null && price.compareTo(MAX_PRICE) < 1 && price.compareTo(MIN_PRICE) > -1;
    }
}
