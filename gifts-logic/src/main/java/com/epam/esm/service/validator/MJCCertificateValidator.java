package com.epam.esm.service.validator;

import com.epam.esm.entity.Certificate;

public interface MJCCertificateValidator {
    void validateCertificate(Certificate certificate);
}
