package com.epam.esm.service.checker;

import com.epam.esm.entity.Certificate;

public interface MJCCertificateDuplicationChecker {
    boolean checkCertificateForAddingDuplication(Certificate certificate);
    boolean checkCertificateForUpdatingDuplication(Certificate certificate);
}
