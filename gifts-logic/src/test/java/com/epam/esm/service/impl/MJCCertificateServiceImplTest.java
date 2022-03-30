package com.epam.esm.service.impl;

import com.epam.esm.dao.MJCCertificateDao;
import com.epam.esm.dao.MJCTagDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.MJCTagService;
import com.epam.esm.service.checker.MJCCertificateDuplicationChecker;
import com.epam.esm.service.collector.MJCCertificateFullDataCollector;
import com.epam.esm.service.constant.MJCPaginationConstant;
import com.epam.esm.service.exception.MJCEntityDuplicationException;
import com.epam.esm.service.exception.MJCInvalidEntityException;
import com.epam.esm.service.exception.MJCUnknownEntityException;
import com.epam.esm.service.handler.MJCPaginationParametersHandler;
import com.epam.esm.service.validator.MJCCertificateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;

class MJCCertificateServiceImplTest {
    private MJCCertificateServiceImpl certificateService;
    private MJCCertificateValidator certificateValidator;
    private MJCCertificateDuplicationChecker certificateDuplicationChecker;
    private MJCCertificateDao certificateDao;
    private MJCCertificateFullDataCollector certificateFullDataCollector;
    private MJCPaginationParametersHandler paginationParametersHandler;
    private MJCTagService tagService;
    private MJCTagDao tagDao;
    private Certificate firstTestCertificate;
    private Certificate secondTestCertificate;
    private List<Certificate> certificates;

    @BeforeEach
    void setUp() {
        tagService = mock(MJCTagService.class);
        certificateDao = mock(MJCCertificateDao.class);
        certificateValidator = mock(MJCCertificateValidator.class);
        certificateDuplicationChecker = mock(MJCCertificateDuplicationChecker.class);
        certificateFullDataCollector = mock(MJCCertificateFullDataCollector.class);
        paginationParametersHandler = mock(MJCPaginationParametersHandler.class);
        tagDao = mock(MJCTagDao.class);
        certificateService = new MJCCertificateServiceImpl(certificateDao, tagDao, tagService, certificateValidator,
                certificateDuplicationChecker, certificateFullDataCollector, paginationParametersHandler);

        Tag firstTestTag = new Tag(1, "Jumps");
        Tag secondTestTag = new Tag(2, "Fly");
        Tag thirdTestTag = new Tag(3, "Entertainment");

        firstTestCertificate = new Certificate(2, "Jump park", "Free jumps for your health!",
                new BigDecimal("30"), (short) 30, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>(Arrays.asList(firstTestTag, thirdTestTag)));
        secondTestCertificate = new Certificate(1, "Fly tube", "Free flying in air tube!",
                new BigDecimal("70"), (short) 30, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>(Arrays.asList(secondTestTag, thirdTestTag)));
        Certificate thirdTestCertificate = new Certificate(666, "DeletedCertificate", "U cant read the certificate!",
                new BigDecimal("666"), (short) 13, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>(Arrays.asList(new Tag(666, "Satan"), new Tag(13, "Hell"))));
        thirdTestCertificate.setDeleted(true);

        certificates = Arrays.asList(firstTestCertificate, secondTestCertificate, thirdTestCertificate);
    }

    @Test
    void addCertificatePositiveTest() {
        doNothing().when(certificateValidator).validateCertificate(firstTestCertificate);
        when(certificateDuplicationChecker.checkCertificateForAddingDuplication(firstTestCertificate)).thenReturn(true);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.findMaxCertificateId()).thenReturn(2L);
        MJCCertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(Set.class));

        Assertions.assertEquals(spyCertificateService.addCertificate(firstTestCertificate), firstTestCertificate);
    }

    @Test
    void addInvalidCertificateTest() {
        doThrow(MJCInvalidEntityException.class).when(certificateValidator).validateCertificate(firstTestCertificate);
        when(certificateDuplicationChecker.checkCertificateForAddingDuplication(firstTestCertificate)).thenReturn(true);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.findMaxCertificateId()).thenReturn(2L);
        MJCCertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(Set.class));

        Assertions.assertThrows(MJCInvalidEntityException.class, () -> spyCertificateService.addCertificate(firstTestCertificate));
    }

    @Test
    void addDuplicateCertificateTest() {
        doNothing().when(certificateValidator).validateCertificate(firstTestCertificate);
        when(certificateDuplicationChecker.checkCertificateForAddingDuplication(firstTestCertificate)).thenReturn(false);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        MJCCertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(Set.class));

        Assertions.assertThrows(MJCEntityDuplicationException.class, () -> spyCertificateService.addCertificate(firstTestCertificate));
    }

    @Test
    void findAllCertificatesWithoutParametersTest() {
        Map<String, String> parameters = new HashMap<>();

        Map<String, Integer> handledPaginationParameters = new HashMap<>();
        handledPaginationParameters.put(MJCPaginationConstant.PAGE_PARAMETER, 1);
        handledPaginationParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, 2);

        when(certificateDao.findAll(1, 2)).thenReturn(certificates);
        when(paginationParametersHandler.handlePaginationParameters(parameters)).thenReturn(handledPaginationParameters);
        Assertions.assertEquals(certificateService.findAllCertificates(parameters, new ArrayList<>()), Arrays.asList(firstTestCertificate, secondTestCertificate));
    }

    @Test
    void findCertificateByIdPositiveTest() {
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        Assertions.assertEquals(certificateService.findCertificateById(2), firstTestCertificate);
    }

    @Test
    void findUnknownCertificateByIdTest() {
        when(certificateDao.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> certificateService.findCertificateById(2));
    }

    @Test
    void removeNonExistCertificateTest() {
        when(certificateDao.findById(2)).thenReturn(Optional.empty());

        Assertions.assertThrows(MJCUnknownEntityException.class, () -> certificateService.removeCertificateById(2));
    }

    @Test
    void updateCertificatePositiveTest() {
        doNothing().when(certificateValidator).validateCertificate(firstTestCertificate);
        when(certificateDuplicationChecker.checkCertificateForUpdatingDuplication(firstTestCertificate)).thenReturn(true);
        when(certificateDao.update(firstTestCertificate)).thenReturn(firstTestCertificate);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.clearCertificateTags(2)).thenReturn(true);
        MJCCertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(Set.class));
        Assertions.assertEquals(spyCertificateService.updateCertificate(firstTestCertificate), firstTestCertificate);
    }

    @Test
    void updateInvalidCertificateTest() {
        doThrow(MJCInvalidEntityException.class).when(certificateValidator).validateCertificate(firstTestCertificate);
        when(certificateDuplicationChecker.checkCertificateForUpdatingDuplication(firstTestCertificate)).thenReturn(true);
        when(certificateDao.update(firstTestCertificate)).thenReturn(firstTestCertificate);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.clearCertificateTags(2)).thenReturn(true);
        MJCCertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(Set.class));
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> spyCertificateService.updateCertificate(firstTestCertificate));
    }

    @Test
    void updateDuplicateCertificateTest() {
        doNothing().when(certificateValidator).validateCertificate(firstTestCertificate);
        when(certificateDuplicationChecker.checkCertificateForUpdatingDuplication(firstTestCertificate)).thenReturn(false);
        when(certificateDao.update(firstTestCertificate)).thenReturn(firstTestCertificate);
        when(certificateDao.findById(2)).thenReturn(Optional.of(firstTestCertificate));
        when(certificateDao.clearCertificateTags(2)).thenReturn(true);
        MJCCertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doNothing().when(spyCertificateService).addCertificateTags(isA(Long.class), isA(Set.class));
        Assertions.assertThrows(MJCEntityDuplicationException.class, () -> spyCertificateService.updateCertificate(firstTestCertificate));
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
        MJCCertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doReturn(result).when(spyCertificateService).updateCertificate(result);
        Assertions.assertEquals(spyCertificateService.patchCertificate(testCertificate), result);
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
        MJCCertificateServiceImpl spyCertificateService = Mockito.spy(certificateService);
        doReturn(result).when(spyCertificateService).updateCertificate(result);
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> spyCertificateService.patchCertificate(testCertificate));
    }
}