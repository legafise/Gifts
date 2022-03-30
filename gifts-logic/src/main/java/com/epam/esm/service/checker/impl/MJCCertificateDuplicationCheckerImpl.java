package com.epam.esm.service.checker.impl;

import com.epam.esm.dao.MJCCertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.checker.MJCCertificateDuplicationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MJCCertificateDuplicationCheckerImpl implements MJCCertificateDuplicationChecker {
    private final MJCCertificateDao certificateDao;

    @Autowired
    public MJCCertificateDuplicationCheckerImpl(MJCCertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public boolean checkCertificateForAddingDuplication(Certificate certificate) {
        return !certificateDao.findByName(certificate.getName()).isPresent();
    }

    @Override
    public boolean checkCertificateForUpdatingDuplication(Certificate certificate) {
        Optional<Certificate> updatingCertificate = certificateDao.findByName(certificate.getName());
        return !updatingCertificate.isPresent() || updatingCertificate.get().getId() == certificate.getId();
    }
}
