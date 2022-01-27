package com.epam.esm.service.checker.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.checker.CertificateDuplicationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CertificateDuplicationCheckerImpl implements CertificateDuplicationChecker {
    private final CertificateDao certificateDao;

    @Autowired
    public CertificateDuplicationCheckerImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public boolean checkCertificateForAddingDuplication(Certificate certificate) {
        return certificateDao.findAll().stream()
                .noneMatch(currentCertificate -> currentCertificate.getName()
                        .toUpperCase().equals(certificate.getName()));
    }

    @Override
    public boolean checkCertificateForUpdatingDuplication(Certificate certificate, String certificateId) {
        return certificateDao.findAll().stream()
                .filter(currentCertificate -> currentCertificate.getName().toUpperCase()
                        .equals(certificate.getName().toUpperCase()))
                .findFirst()
                .map(currentCertificate -> currentCertificate.getId() == Long.parseLong(certificateId))
                .orElse(true);
    }
}
