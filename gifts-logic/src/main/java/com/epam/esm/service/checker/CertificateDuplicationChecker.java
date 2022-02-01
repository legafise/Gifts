package com.epam.esm.service.checker;

import com.epam.esm.entity.Certificate;

public interface CertificateDuplicationChecker {
    boolean checkCertificateForAddingDuplication(Certificate certificate);
    boolean checkCertificateForUpdatingDuplication(Certificate certificate);
}
