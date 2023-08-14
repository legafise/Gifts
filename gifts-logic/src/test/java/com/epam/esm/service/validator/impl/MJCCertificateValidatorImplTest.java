package com.epam.esm.service.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.MJCInvalidEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

class MJCCertificateValidatorImplTest {
    private MJCCertificateValidatorImpl certificateValidator;
    private Certificate testCertificate;

    @BeforeEach
    void setUp() {
        certificateValidator = new MJCCertificateValidatorImpl();

        Tag firstTestTag = new Tag(1, "Jumps");
        Tag thirdTestTag = new Tag(3, "Entertainment");

        testCertificate = new Certificate(2, "Jump park", "Free jumps for your health!",
                new BigDecimal("30"), (short) 30, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>(Arrays.asList(firstTestTag, thirdTestTag)));
    }

    @Test
    void validateCertificatePositiveTest() {
        certificateValidator.validateCertificate(testCertificate);
    }

    @Test
    void validateCertificateWithInvalidNameTest() {
        testCertificate.setName("d");
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithNullNameTest() {
        testCertificate.setName(null);
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidDescriptionTest() {
        testCertificate.setDescription("test");
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithNullDescriptionTest() {
        testCertificate.setDescription(null);
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidPriceTest() {
        testCertificate.setPrice(new BigDecimal("0"));
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithNullPriceTest() {
        testCertificate.setPrice(null);
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidDurationTest() {
        testCertificate.setDuration((short) 1);
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidCreateDateTest() {
        testCertificate.setCreateDate(null);
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidLastUpdateDateTest() {
        testCertificate.setLastUpdateDate(null);
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidTagsTest() {
        testCertificate.setTags(null);
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }
}