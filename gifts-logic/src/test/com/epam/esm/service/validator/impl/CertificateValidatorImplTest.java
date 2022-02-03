package com.epam.esm.service.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.InvalidCertificateException;
import com.epam.esm.service.validator.CertificateValidator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CertificateValidatorImplTest {
    private CertificateValidatorImpl certificateValidator;
    private Certificate testCertificate;

    @BeforeEach
    void setUp() {
        certificateValidator = new CertificateValidatorImpl();

        Tag firstTestTag = new Tag(1, "Jumps");
        Tag thirdTestTag = new Tag(3, "Entertainment");

        testCertificate = new Certificate(2, "Jump park", "Free jumps for your health!",
                new BigDecimal("30"), (short) 30, LocalDateTime.now(), LocalDateTime.now(), Arrays.asList(firstTestTag, thirdTestTag));
    }

    @Test
    void validateCertificatePositiveTest() {
        certificateValidator.validateCertificate(testCertificate);
    }

    @Test
    void validateCertificateWithInvalidNameTest() {
        testCertificate.setName("d");
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithNullNameTest() {
        testCertificate.setName(null);
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidDescriptionTest() {
        testCertificate.setDescription("test");
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithNullDescriptionTest() {
        testCertificate.setDescription(null);
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidPriceTest() {
        testCertificate.setPrice(new BigDecimal("0"));
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithNullPriceTest() {
        testCertificate.setPrice(null);
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidDurationTest() {
        testCertificate.setDuration((short) 1);
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidCreateDateTest() {
        testCertificate.setCreateDate(null);
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidLastUpdateDateTest() {
        testCertificate.setLastUpdateDate(null);
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }

    @Test
    void validateCertificateWithInvalidTagsTest() {
        testCertificate.setTags(null);
        Assert.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
    }
}