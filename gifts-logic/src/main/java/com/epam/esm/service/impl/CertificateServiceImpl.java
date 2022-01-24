package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.collector.CertificateFullDataCollector;
import com.epam.esm.service.validator.CertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private static final String NONEXISTENT_CERTIFICATE_MESSAGE = "Nonexistent certificate";
    private static final String INVALID_CERTIFICATE_MESSAGE = "Invalid certificate";
    private static final String INVALID_CERTIFICATE_ID_MESSAGE = "Invalid certificate id";
    private final CertificateDao certificateDao;
    private final TagService tagService;
    private final CertificateValidator certificateValidator;
    private final CertificateFullDataCollector certificateFullDataCollector;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagService tagService, CertificateValidator certificateValidator, CertificateFullDataCollector certificateFullDataCollector) {
        this.certificateDao = certificateDao;
        this.tagService = tagService;
        this.certificateValidator = certificateValidator;
        this.certificateFullDataCollector = certificateFullDataCollector;
    }

    @Override
    @Transactional
    public Certificate addCertificate(Certificate certificate) {
        try {
            if (certificateValidator.validateCertificate(certificate) && certificateDao.add(certificate)) {
                long addedCertificateId = Collections.max(certificateDao.findAll().stream()
                        .map(Certificate::getId)
                        .collect(Collectors.toList()));
                addCertificateTags(addedCertificateId, certificate.getTags());
                return certificateDao.findById(addedCertificateId).get();
            }
        } catch (ParseException e) {
            throw new ServiceException(e);
        }

        throw new ServiceException(INVALID_CERTIFICATE_MESSAGE);
    }

    @Override
    public List<Certificate> findAllCertificates() {
        return certificateDao.findAll();
    }

    @Override
    public Certificate findCertificateById(String id) {
        Optional<Certificate> certificate = certificateDao.findById(Long.parseLong(id));
        if (certificate.isPresent()) {
            return certificate.get();
        }

        throw new ServiceException(NONEXISTENT_CERTIFICATE_MESSAGE);
    }

    @Override
    @Transactional
    public Certificate updateCertificate(Certificate certificate, String id) {
        try {
            if (certificateValidator.validateCertificate(certificate)
                    && certificateDao.update(certificate, Long.parseLong(id))) {
                if (certificate.getTags().size() >= 1) {
                    certificateDao.clearCertificateTags(Long.parseLong(id));
                    addCertificateTags(Long.parseLong(id), certificate.getTags());
                }

                return findCertificateById(id);
            }
        } catch (ParseException e) {
            throw new ServiceException(e);
        }

        throw new ServiceException(INVALID_CERTIFICATE_MESSAGE);
    }

    @Override
    @Transactional
    public boolean removeCertificateById(String id) {
        certificateDao.clearCertificateTags(Long.parseLong(id));
        if (certificateDao.remove(Long.parseLong(id))) {
            return true;
        }

        throw new ServiceException(INVALID_CERTIFICATE_ID_MESSAGE);
    }

    @Override
    public Certificate patchCertificate(Certificate certificate, String id) {
        Optional<Certificate> actualCertificate = certificateDao.findById(Long.parseLong(id));
        if (actualCertificate.isPresent()) {
            return updateCertificate(certificateFullDataCollector
                    .collectFullCertificateData(certificate, actualCertificate.get()), id);
        }

        throw new ServiceException(INVALID_CERTIFICATE_ID_MESSAGE);
    }

    private void addCertificateTags(long certificateId, List<Tag> tags) {
        if (!tags.isEmpty()) {
            tags.forEach(tag -> {
                tagService.addTagIfNotExists(tag);
                Tag currentTag = tagService.findTagByName(tag.getName());
                certificateDao.addTagToCertificate(certificateId, currentTag.getId());
            });
        }
    }
}
