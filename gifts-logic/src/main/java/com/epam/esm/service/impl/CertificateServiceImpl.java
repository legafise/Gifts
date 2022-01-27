package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.checker.CertificateDuplicationChecker;
import com.epam.esm.service.collector.CertificateFullDataCollector;
import com.epam.esm.service.exception.DuplicateCertificateException;
import com.epam.esm.service.exception.InvalidCertificateDateFormatException;
import com.epam.esm.service.exception.InvalidCertificateException;
import com.epam.esm.service.exception.UnknownCertificateException;
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
    private static final String NONEXISTENT_CERTIFICATE_MESSAGE = "nonexistent.certificate";
    private static final String INVALID_CERTIFICATE_MESSAGE = "invalid.certificate";
    private static final String INVALID_CERTIFICATE_DATE_FORMAT_MESSAGE = "invalid.date.format";
    private static final String DUPLICATE_CERTIFICATE_MESSAGE = "duplicate.certificate";
    private final CertificateDao certificateDao;
    private final TagService tagService;
    private final CertificateValidator certificateValidator;
    private final CertificateDuplicationChecker certificateDuplicationChecker;
    private final CertificateFullDataCollector certificateFullDataCollector;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagService tagService, CertificateValidator certificateValidator, CertificateDuplicationChecker certificateDuplicationChecker, CertificateFullDataCollector certificateFullDataCollector) {
        this.certificateDao = certificateDao;
        this.tagService = tagService;
        this.certificateValidator = certificateValidator;
        this.certificateDuplicationChecker = certificateDuplicationChecker;
        this.certificateFullDataCollector = certificateFullDataCollector;
    }

    @Override
    @Transactional
    public Certificate addCertificate(Certificate certificate) {
        try {
            if (certificateValidator.validateCertificate(certificate)) {
                if (certificateDuplicationChecker.checkCertificateForAddingDuplication(certificate)
                        && certificateDao.add(certificate)) {
                    long addedCertificateId = Collections.max(certificateDao.findAll().stream()
                            .map(Certificate::getId)
                            .collect(Collectors.toList()));
                    addCertificateTags(addedCertificateId, certificate.getTags());
                    return certificateDao.findById(addedCertificateId).get();
                }

                throw new DuplicateCertificateException(INVALID_CERTIFICATE_MESSAGE);
            }
        } catch (ParseException e) {
            throw new InvalidCertificateDateFormatException(INVALID_CERTIFICATE_DATE_FORMAT_MESSAGE);
        }

        throw new InvalidCertificateException(INVALID_CERTIFICATE_MESSAGE);
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

        throw new UnknownCertificateException(NONEXISTENT_CERTIFICATE_MESSAGE);
    }

    @Override
    public List<Certificate> findCertificatesByTagName(String tagName) {
        return certificateDao.findByTagName(tagName);
    }

    @Override
    public List<Certificate> findCertificatesByNamePart(String name) {
        return certificateDao.findByNamePart(name);
    }

    @Override
    @Transactional
    public Certificate updateCertificate(Certificate certificate, String id) {
        try {
            if (certificateValidator.validateCertificate(certificate)) {
                if (certificateDuplicationChecker.checkCertificateForUpdatingDuplication(certificate, id)
                        && certificateDao.update(certificate, Long.parseLong(id))) {
                    updateCertificateTags(certificate, id);
                    return findCertificateById(id);
                }

                throw new DuplicateCertificateException(DUPLICATE_CERTIFICATE_MESSAGE);
            }
        } catch (ParseException e) {
            throw new InvalidCertificateDateFormatException(INVALID_CERTIFICATE_DATE_FORMAT_MESSAGE);
        }

        throw new InvalidCertificateException(INVALID_CERTIFICATE_MESSAGE);
    }

    @Override
    @Transactional
    public boolean removeCertificateById(String id) {
        certificateDao.clearCertificateTags(Long.parseLong(id));
        if (certificateDao.remove(Long.parseLong(id))) {
            return true;
        }

        throw new UnknownCertificateException(NONEXISTENT_CERTIFICATE_MESSAGE);
    }

    @Override
    @Transactional
    public Certificate patchCertificate(Certificate certificate, String id) {
        Optional<Certificate> actualCertificate = certificateDao.findById(Long.parseLong(id));
        if (actualCertificate.isPresent()) {
            return updateCertificate(certificateFullDataCollector
                    .collectFullCertificateData(certificate, actualCertificate.get()), id);
        }

        throw new UnknownCertificateException(NONEXISTENT_CERTIFICATE_MESSAGE);
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

    private void updateCertificateTags(Certificate certificate, String certificateId) {
        if (certificate.getTags().size() >= 1) {
            certificateDao.clearCertificateTags(Long.parseLong(certificateId));
            addCertificateTags(Long.parseLong(certificateId), certificate.getTags());
        }
    }
}