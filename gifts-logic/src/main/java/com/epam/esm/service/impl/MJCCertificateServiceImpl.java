package com.epam.esm.service.impl;

import com.epam.esm.dao.MJCCertificateDao;
import com.epam.esm.dao.MJCTagDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.MJCCertificateService;
import com.epam.esm.service.MJCTagService;
import com.epam.esm.service.checker.MJCCertificateDuplicationChecker;
import com.epam.esm.service.collector.MJCCertificateFullDataCollector;
import com.epam.esm.service.constant.MJCPaginationConstant;
import com.epam.esm.service.exception.MJCEntityDuplicationException;
import com.epam.esm.service.exception.MJCUnknownEntityException;
import com.epam.esm.service.handler.MJCCertificatesHandler;
import com.epam.esm.service.handler.MJCPaginationParametersHandler;
import com.epam.esm.service.validator.MJCCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MJCCertificateServiceImpl implements MJCCertificateService {
    private static final String NONEXISTENT_CERTIFICATE_MESSAGE = "nonexistent.certificate";
    private static final String DUPLICATE_CERTIFICATE_MESSAGE = "duplicate.certificate";
    private static final String UNKNOWN_TAGS_WAS_RECEIVED_MESSAGE = "unknown.tags.was.received";
    private final MJCCertificateDao certificateDao;
    private final MJCTagDao tagDao;
    private final MJCTagService tagService;
    private final MJCCertificateValidator certificateValidator;
    private final MJCCertificateDuplicationChecker certificateDuplicationChecker;
    private final MJCCertificateFullDataCollector certificateFullDataCollector;
    private final MJCPaginationParametersHandler paginationParametersHandler;

    @Autowired
    public MJCCertificateServiceImpl(MJCCertificateDao certificateDao, MJCTagDao tagDao, MJCTagService tagService,
                                     MJCCertificateValidator certificateValidator, MJCCertificateDuplicationChecker certificateDuplicationChecker,
                                     MJCCertificateFullDataCollector certificateFullDataCollector, MJCPaginationParametersHandler paginationParametersHandler) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.tagService = tagService;
        this.certificateValidator = certificateValidator;
        this.certificateDuplicationChecker = certificateDuplicationChecker;
        this.certificateFullDataCollector = certificateFullDataCollector;
        this.paginationParametersHandler = paginationParametersHandler;
    }

    @Override
    @Transactional
    public Certificate addCertificate(Certificate certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificate.setDeleted(false);

        certificateValidator.validateCertificate(certificate);
        if (!certificateDuplicationChecker.checkCertificateForAddingDuplication(certificate)) {
            throw new MJCEntityDuplicationException(Certificate.class, DUPLICATE_CERTIFICATE_MESSAGE);
        }

        Set<Tag> tags = certificate.getTags();
        certificate.setTags(new HashSet<>());
        certificateDao.add(certificate);
        long addedCertificateId = certificateDao.findMaxCertificateId();
        addCertificateTags(addedCertificateId, tags);
        return findCertificateById(addedCertificateId);
    }

    @Override
    public List<Certificate> findAllCertificates(Map<String, String> handleParameters, List<String> tagNames) {
        List<Certificate> certificates = findCertificatesWithPagination(handleParameters);
        certificates = removeDeletedCertificatesFromList(certificates);

        if (tagNames != null && !tagNames.isEmpty()) {
            List<Tag> tags = convertTagNamesToTags(tagNames);
            certificates = certificates.stream()
                    .filter(certificate -> certificate.getTags().stream()
                            .anyMatch(tags::contains))
                    .collect(Collectors.toList());
        }

        handleCertificates(certificates, handleParameters);

        return certificates;
    }

    @Override
    public Certificate findCertificateById(long id) {
        Optional<Certificate> certificate = certificateDao.findById(id);
        if (!certificate.isPresent() || certificate.get().isDeleted()) {
            throw new MJCUnknownEntityException(Certificate.class, NONEXISTENT_CERTIFICATE_MESSAGE);
        }

        return certificate.get();
    }

    @Override
    @Transactional
    public Certificate updateCertificate(Certificate certificate) {
        Certificate actualCertificate = findCertificateById(certificate.getId());

        if (certificate.getTags().isEmpty()) {
            certificate.setTags(new HashSet<>(actualCertificate.getTags()));
        }
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificate.setCreateDate(actualCertificate.getCreateDate());

        certificateValidator.validateCertificate(certificate);
        if (!certificateDuplicationChecker.checkCertificateForUpdatingDuplication(certificate)) {
            throw new MJCEntityDuplicationException(Certificate.class, DUPLICATE_CERTIFICATE_MESSAGE);
        }

        Set<Tag> tags = certificate.getTags();
        certificate.setTags(new HashSet<>());
        certificateDao.update(certificate);
        addCertificateTags(certificate.getId(), tags);
        return findCertificateById(certificate.getId());
    }

    @Override
    @Transactional
    public void removeCertificateById(long id) {
        Optional<Certificate> removingCertificate = certificateDao.findById(id);
        if (!removingCertificate.isPresent() || removingCertificate.get().isDeleted()) {
            throw new MJCUnknownEntityException(Certificate.class, NONEXISTENT_CERTIFICATE_MESSAGE);
        }

        removingCertificate.get().setDeleted(true);
        certificateDao.update(removingCertificate.get());
    }

    @Override
    @Transactional
    public Certificate patchCertificate(Certificate certificate) {
        return updateCertificate(certificateFullDataCollector
                .collectFullCertificateData(certificate, findCertificateById(certificate.getId())));
    }

    public void addCertificateTags(long certificateId, Set<Tag> tags) {
        if (!tags.isEmpty()) {
            certificateDao.clearCertificateTags(certificateId);
            tags.forEach(tag -> {
                tagService.addTagIfNotExists(tag);
                Tag currentTag = tagService.findTagByName(tag.getName());
                certificateDao.addTagToCertificate(certificateId, currentTag.getId());
            });
        }
    }

    private List<Certificate> findCertificatesWithPagination(Map<String, String> handleParameters) {
        Map<String, Integer> handledPaginationParameters = paginationParametersHandler.handlePaginationParameters(handleParameters);

        return certificateDao.findAll(handledPaginationParameters.get(MJCPaginationConstant.PAGE_PARAMETER),
                handledPaginationParameters.get(MJCPaginationConstant.PAGE_SIZE_PARAMETER));
    }

    private List<Tag> convertTagNamesToTags(List<String> tagNames) {
        List<Tag> tags = tagNames.stream()
                .map(tagDao::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        if (tags.isEmpty()) {
            throw new MJCUnknownEntityException(Tag.class, UNKNOWN_TAGS_WAS_RECEIVED_MESSAGE);
        }

        return tags;
    }

    private void handleCertificates(List<Certificate> certificates, Map<String, String> handleParameters) {
        if (!handleParameters.isEmpty()) {
            for (Map.Entry<String, String> parametersEntry : handleParameters.entrySet()) {
                certificates = MJCCertificatesHandler
                        .findHandlerByName(parametersEntry.getKey())
                        .handle(certificates, parametersEntry.getValue());
            }
        }
    }

    private List<Certificate> removeDeletedCertificatesFromList(List<Certificate> certificates) {
        return certificates.stream()
                .filter(certificate -> !certificate.isDeleted())
                .collect(Collectors.toList());
    }
}