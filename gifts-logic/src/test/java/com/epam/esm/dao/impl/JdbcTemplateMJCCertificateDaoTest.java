package com.epam.esm.dao.impl;

import com.epam.esm.TestApplication;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles({"template-test"})
@Transactional
class JdbcTemplateMJCCertificateDaoTest {
    @Autowired
    private JdbcTemplateMJCCertificateDao certificateDao;
    private Certificate testCertificate;
    private Certificate firstTestCertificate;
    private Certificate secondTestCertificate;

    @BeforeEach
    void setUp() {
        testCertificate = new Certificate(1, "Test", "Test certificate",
                new BigDecimal("100.00"), (short) 61, LocalDateTime.parse("2022-03-15T21:30"),
                LocalDateTime.parse("2022-06-15T21:30"), Collections.emptySet());
        firstTestCertificate = new Certificate(102, "Jump park", "Free jumps at trampolines",
                new BigDecimal("35.00"), (short) 30, LocalDateTime.parse("2022-03-15T21:30"),
                LocalDateTime.parse("2022-06-15T21:30"), Collections.emptySet());
        secondTestCertificate = new Certificate(101, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), Collections.emptySet());
    }

    @Test
    void addCertificateTest() {
        certificateDao.add(testCertificate);
        Assertions.assertEquals(testCertificate, certificateDao.findByName(testCertificate.getName()).get());
    }

    @Test
    void findCertificateByIdTest() {
        Assertions.assertEquals(certificateDao.findById(102).get(), firstTestCertificate);
    }

    @Test
    void findAllCertificatesTest() {
        Assertions.assertEquals(Arrays.asList(secondTestCertificate, firstTestCertificate), certificateDao.findAll(1, 2));
    }

    @Test
    void findCertificateByNameTest() {
        Assertions.assertEquals(certificateDao.findByName("Jump park").get(), firstTestCertificate);
    }

    @Test
    void updateTagTest() {
        secondTestCertificate.setName("Tattoo");
        Assertions.assertEquals(secondTestCertificate, certificateDao.update(secondTestCertificate));
    }

    @Test
    void removeCertificateTest() {
        firstTestCertificate.setName("CertificateForRemoving");
        certificateDao.add(firstTestCertificate);
        long addedCertificateId = certificateDao.findByName(firstTestCertificate.getName()).get().getId();
        certificateDao.remove(addedCertificateId);
        Assertions.assertFalse(certificateDao.findById(addedCertificateId).isPresent());
    }

    @Test
    void addTagToCertificateTest() {
        Assertions.assertTrue(certificateDao.addTagToCertificate(101, 103));
    }

    @Test
    void clearCertificateTagsTest() {
        Assertions.assertTrue(certificateDao.clearCertificateTags(101));
    }

    @Test
    void findMaxCertificateIdTest() {
        Assertions.assertEquals(103, certificateDao.findMaxCertificateId());
    }
}