package com.epam.esm.dao.impl;

import com.epam.esm.config.TestSpringConfig;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestSpringConfig.class})
class CertificateDaoImplTest {
    @Autowired
    private CertificateDaoImpl certificateDao;
    private Certificate testCertificate;
    private Certificate firstTestCertificate;
    private Certificate secondTestCertificate;

    @BeforeEach
    void setUp() {
        Tag firstTestTag = new Tag(102, "Jumps");
        Tag secondTestTag = new Tag(103, "Entertainment");

        testCertificate = new Certificate(1, "Test", "Test certificate",
                new BigDecimal("100.00"), (short) 61, LocalDateTime.parse("2022-03-15T00:00"),
                LocalDateTime.parse("2022-06-15T00:00"));
        firstTestCertificate = new Certificate(102, "Jump park", "Free jumps at trampolines",
                new BigDecimal("35.00"), (short) 30, LocalDateTime.parse("2022-03-15T00:00"),
                LocalDateTime.parse("2022-06-15T00:00"), Arrays.asList(firstTestTag, secondTestTag));
        secondTestCertificate = new Certificate(101, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-03-15T00:00"),
                LocalDateTime.parse("2022-06-15T00:00"), Collections.singletonList(secondTestTag));
    }

    @Test
    void addCertificateTest() {
        Assert.assertTrue(certificateDao.add(testCertificate));
    }

    @Test
    void findCertificateByIdTest() {
        Assert.assertEquals(certificateDao.findById(102).get(), firstTestCertificate);
    }

    @Test
    void updateTagTest() {
        secondTestCertificate.setName("Tattoo");
        Assert.assertTrue(certificateDao.update(secondTestCertificate));
    }

    @Test
    void removeTagTest() {
        Assert.assertTrue(certificateDao.remove(103));
    }

    @Test
    void addTagToCertificateTest() {
        Assert.assertTrue(certificateDao.addTagToCertificate(101, 103));
    }

    @Test
    void clearCertificateTagsTest() {
        Assert.assertTrue(certificateDao.clearCertificateTags(101));
    }
}