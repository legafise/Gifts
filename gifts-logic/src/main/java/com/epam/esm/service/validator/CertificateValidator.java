package com.epam.esm.service.validator;

import com.epam.esm.entity.Certificate;

public interface CertificateValidator {
    boolean validateCertificate(Certificate certificate);
}
