package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.checker.CertificateDuplicationChecker;
import com.epam.esm.service.collector.CertificateFullDataCollector;
import com.epam.esm.service.exception.DuplicateCertificateException;
import com.epam.esm.service.exception.InvalidCertificateException;
import com.epam.esm.service.exception.UnknownCertificateException;
import com.epam.esm.service.validator.CertificateValidator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CertificateServiceImplTest {
    private CertificateServiceImpl certificateService;
    private CertificateValidator certificateValidator;
    private CertificateDuplicationChecker certificateDuplicationChecker;
    private CertificateDao certificateDao;
    private CertificateFullDataCollector certificateFullDataCollector;
    private TagService tagService;
    private Certificate firstTestCertificate;
    private List<Certificate> certificates;

    @BeforeEach
    void setUp() {
        tagService = mock(TagService.class);
        certificateDao = mock(CertificateDao.class);
        certificateValidator = mock(CertificateValidator.class);
        certificateDuplicationChecker = mock(CertificateDuplicationChecker.class);
        certificateFullDataCollector = mock(CertificateFullDataCollector.class);
        certificateService = new CertificateServiceImpl(certificateDao, tagService, certificateValidator,
                certificateDuplicationChecker, certificateFullDataCollector);

        Tag firstTestTag = new Tag(1, "Jumps");
        Tag secondTestTag = new Tag(2, "Fly");
        Tag thirdTestTag = new Tag(3, "Entertainment");

        firstTestCertificate = new Certificate(2, "Jump park", "Free jumps for your health!",
                new BigDecimal("30"), (short) 30, LocalDateTime.now(), LocalDateTime.now(), Arrays.asList(firstTestTag, thirdTestTag));
        Certificate secondTestCertificate = new Certificate(1, "Fly tube", "Free flying in air tube!",
                new BigDecimal("70"), (short) 30, LocalDateTime.now(), LocalDateTime.now(), Arrays.asList(secondTestTag, thirdTestTag));

        certificates = Arrays.asList(firstTestCertificate, secondTestCertificate);
    }

    @Test
    void addCertificatePositiveTest() {
        when(certificateValidator.validateCertificate(firstTestCertificate)).thenReturn(true);
        when(certificateDuplicationChecker.checkCertificateForAddingDuplication(firstTestCertificate)).thenReturn(true);
        when(certificateDao.add(firstTestCertificate)).thenReturn(true);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.findAll()).thenReturn(certificates);
        CertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(List.class));

        Assert.assertEquals(spyCertificateService.addCertificate(firstTestCertificate), firstTestCertificate);
    }

    @Test
    void addInvalidCertificateTest() {
        when(certificateValidator.validateCertificate(firstTestCertificate)).thenReturn(false);
        when(certificateDuplicationChecker.checkCertificateForAddingDuplication(firstTestCertificate)).thenReturn(true);
        when(certificateDao.add(firstTestCertificate)).thenReturn(true);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.findAll()).thenReturn(certificates);
        CertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(List.class));

        Assert.assertThrows(InvalidCertificateException.class, () -> spyCertificateService.addCertificate(firstTestCertificate));
    }

    @Test
    void addDuplicateCertificateTest() {
        when(certificateValidator.validateCertificate(firstTestCertificate)).thenReturn(true);
        when(certificateDuplicationChecker.checkCertificateForAddingDuplication(firstTestCertificate)).thenReturn(false);
        when(certificateDao.add(firstTestCertificate)).thenReturn(true);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.findAll()).thenReturn(certificates);
        CertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(List.class));

        Assert.assertThrows(DuplicateCertificateException.class, () -> spyCertificateService.addCertificate(firstTestCertificate));
    }

    @Test
    void findAllCertificatesWithoutParametersTest() {
        when(certificateDao.findAll()).thenReturn(certificates);
        Assert.assertEquals(certificateService.findAllCertificates(new HashMap<>()), certificates);
    }

    @Test
    void findCertificateByIdPositiveTest() {
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        Assert.assertEquals(certificateService.findCertificateById(2), firstTestCertificate);
    }

    @Test
    void findUnknownCertificateByIdTest() {
        when(certificateDao.findById(2)).thenReturn(Optional.empty());
        Assert.assertThrows(UnknownCertificateException.class, () -> certificateService.findCertificateById(2));
    }

    @Test
    void removeCertificateByIdPositiveTest() {
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.remove(2)).thenReturn(true);

        Assert.assertTrue(certificateService.removeCertificateById(2));
    }

    @Test
    void removeNonExistCertificateTest() {
        when(certificateDao.findById(2)).thenReturn(Optional.empty());
        when(certificateDao.remove(2)).thenReturn(false);

        Assert.assertThrows(UnknownCertificateException.class, () -> certificateService.removeCertificateById(2));
    }

    @Test
    void updateCertificatePositiveTest() {
        when(certificateValidator.validateCertificate(firstTestCertificate)).thenReturn(true);
        when(certificateDuplicationChecker.checkCertificateForUpdatingDuplication(firstTestCertificate)).thenReturn(true);
        when(certificateDao.update(firstTestCertificate)).thenReturn(true);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.clearCertificateTags(2)).thenReturn(true);
        CertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(List.class));
        Assert.assertEquals(spyCertificateService.updateCertificate(firstTestCertificate), firstTestCertificate);
    }

    @Test
    void updateInvalidCertificateTest() {
        when(certificateValidator.validateCertificate(firstTestCertificate)).thenReturn(false);
        when(certificateDuplicationChecker.checkCertificateForUpdatingDuplication(firstTestCertificate)).thenReturn(true);
        when(certificateDao.update(firstTestCertificate)).thenReturn(true);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.clearCertificateTags(2)).thenReturn(true);
        CertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(List.class));
        Assert.assertThrows(InvalidCertificateException.class, () -> spyCertificateService.updateCertificate(firstTestCertificate));
    }

    @Test
    void updateDuplicateCertificateTest() {
        when(certificateValidator.validateCertificate(firstTestCertificate)).thenReturn(true);
        when(certificateDuplicationChecker.checkCertificateForUpdatingDuplication(firstTestCertificate)).thenReturn(false);
        when(certificateDao.update(firstTestCertificate)).thenReturn(true);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.clearCertificateTags(2)).thenReturn(true);
        CertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(List.class));
        Assert.assertThrows(DuplicateCertificateException.class, () -> spyCertificateService.updateCertificate(firstTestCertificate));
    }

    @Test
    void patchCertificatePositiveTest() {
        Certificate testCertificate = new Certificate();
        testCertificate.setId(2);
        testCertificate.setName("TestCertificate");

        Certificate result = new Certificate(2, testCertificate.getName(), firstTestCertificate.getDescription(),
                firstTestCertificate.getPrice(), firstTestCertificate.getDuration(), firstTestCertificate.getCreateDate(),
                firstTestCertificate.getLastUpdateDate(), firstTestCertificate.getTags());
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateFullDataCollector.collectFullCertificateData(testCertificate, firstTestCertificate)).thenReturn(result);
        CertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doReturn(result).when(spyCertificateService).updateCertificate(result);
        Assert.assertEquals(spyCertificateService.patchCertificate(testCertificate), result);
    }

    @Test
    void patchUnknownCertificateTest() {
        Certificate testCertificate = new Certificate();
        testCertificate.setId(2);
        testCertificate.setName("TestCertificate");

        Certificate result = new Certificate(2, testCertificate.getName(), firstTestCertificate.getDescription(),
                firstTestCertificate.getPrice(), firstTestCertificate.getDuration(), firstTestCertificate.getCreateDate(),
                firstTestCertificate.getLastUpdateDate(), firstTestCertificate.getTags());
        when(certificateDao.findById(2)).thenReturn(Optional.empty());
        when(certificateFullDataCollector.collectFullCertificateData(testCertificate, firstTestCertificate)).thenReturn(result);
        CertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doReturn(result).when(spyCertificateService).updateCertificate(result);
        Assert.assertThrows(UnknownCertificateException.class, () -> spyCertificateService.patchCertificate(testCertificate));
    }
}