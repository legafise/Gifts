package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.checker.CertificateDuplicationChecker;
import com.epam.esm.service.collector.CertificateFullDataCollector;
import com.epam.esm.service.exception.DuplicateCertificateException;
import com.epam.esm.service.exception.InvalidCertificateException;
import com.epam.esm.service.exception.UnknownCertificateException;
import com.epam.esm.service.handler.CertificatesHandler;
import com.epam.esm.service.validator.CertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private static final String NONEXISTENT_CERTIFICATE_MESSAGE = "nonexistent.certificate";
    private static final String INVALID_CERTIFICATE_MESSAGE = "invalid.certificate";
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
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());

        if (!certificateValidator.validateCertificate(certificate)) {
            throw new InvalidCertificateException(INVALID_CERTIFICATE_MESSAGE);
        }

        if (!certificateDuplicationChecker.checkCertificateForAddingDuplication(certificate)) {
            throw new DuplicateCertificateException(DUPLICATE_CERTIFICATE_MESSAGE);
        }

        certificateDao.add(certificate);
        long addedCertificateId = Collections.max(certificateDao.findAll().stream()
                .map(Certificate::getId)
                .collect(Collectors.toList()));
        addCertificateTags(addedCertificateId, certificate.getTags());
        return certificateDao.findById(addedCertificateId).get();
    }

    @Override
    public List<Certificate> findAllCertificates(Map<String, String> handleParameters) {
        List<Certificate> certificates = certificateDao.findAll();

        if (handleParameters != null && !handleParameters.isEmpty()) {
            for (Map.Entry<String, String> parametersEntry : handleParameters.entrySet()) {
                certificates = CertificatesHandler
                        .findHandlerByName(parametersEntry.getKey())
                        .handle(certificates, parametersEntry.getValue());
            }
        }

        return certificates;
    }

    @Override
    public Certificate findCertificateById(long id) {
        Optional<Certificate> certificate = certificateDao.findById(id);
        if (!certificate.isPresent()) {
            throw new UnknownCertificateException(NONEXISTENT_CERTIFICATE_MESSAGE);
        }

        return certificate.get();
    }

    @Override
    @Transactional
    public Certificate updateCertificate(Certificate certificate) {
        certificate.setLastUpdateDate(LocalDateTime.now());

        if (!certificateValidator.validateCertificate(certificate)) {
            throw new InvalidCertificateException(INVALID_CERTIFICATE_MESSAGE);
        }

        if (!certificateDuplicationChecker.checkCertificateForUpdatingDuplication(certificate)) {
            throw new DuplicateCertificateException(DUPLICATE_CERTIFICATE_MESSAGE);
        }

        certificateDao.update(certificate);
        updateCertificateTags(certificate);
        return findCertificateById(certificate.getId());
    }

    @Override
    @Transactional
    public boolean removeCertificateById(long id) {
        certificateDao.clearCertificateTags(id);
        if (!certificateDao.remove(id)) {
            throw new UnknownCertificateException(NONEXISTENT_CERTIFICATE_MESSAGE);
        }

        return true;
    }

    @Override
    @Transactional
    public Certificate patchCertificate(Certificate certificate) {
        Optional<Certificate> actualCertificate = certificateDao.findById(certificate.getId());
        if (!actualCertificate.isPresent()) {
            throw new UnknownCertificateException(NONEXISTENT_CERTIFICATE_MESSAGE);
        }
        return updateCertificate(certificateFullDataCollector
                .collectFullCertificateData(certificate, actualCertificate.get()));
    }

    private boolean isExistentConnectionBetweenCertificateAndTag(long certificateId, long tagId) {
        Optional<Certificate> certificate = certificateDao.findById(certificateId);
        if (!certificate.isPresent()) {
            throw new UnknownCertificateException(NONEXISTENT_CERTIFICATE_MESSAGE);
        }

        return certificate.get().getTags().stream()
                .noneMatch(tag -> tag.getId() == tagId);
    }

    private void addCertificateTags(long certificateId, List<Tag> tags) {
        if (!tags.isEmpty()) {
            tags.forEach(tag -> {
                tagService.addTagIfNotExists(tag);
                Tag currentTag = tagService.findTagByName(tag.getName());
                if (isExistentConnectionBetweenCertificateAndTag(certificateId, currentTag.getId())) {
                    certificateDao.addTagToCertificate(certificateId, currentTag.getId());
                }
            });
        }
    }

    private void updateCertificateTags(Certificate certificate) {
        if (!certificate.getTags().isEmpty()) {
            certificateDao.clearCertificateTags(certificate.getId());
            addCertificateTags(certificate.getId(), certificate.getTags());
        }
    }
}