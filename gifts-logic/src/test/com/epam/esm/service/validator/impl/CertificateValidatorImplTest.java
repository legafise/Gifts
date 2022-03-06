package com.epam.esm.service.validator.impl;

//class CertificateValidatorImplTest {
//    private CertificateValidatorImpl certificateValidator;
//    private Certificate testCertificate;
//
//    @BeforeEach
//    void setUp() {
//        certificateValidator = new CertificateValidatorImpl();
//
//        Tag firstTestTag = new Tag(1, "Jumps");
//        Tag thirdTestTag = new Tag(3, "Entertainment");
//
//        testCertificate = new Certificate(2, "Jump park", "Free jumps for your health!",
//                new BigDecimal("30"), (short) 30, LocalDateTime.now(), LocalDateTime.now(), Arrays.asList(firstTestTag, thirdTestTag));
//    }
//
//    @Test
//    void validateCertificatePositiveTest() {
//        certificateValidator.validateCertificate(testCertificate);
//    }
//
//    @Test
//    void validateCertificateWithInvalidNameTest() {
//        testCertificate.setName("d");
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//
//    @Test
//    void validateCertificateWithNullNameTest() {
//        testCertificate.setName(null);
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//
//    @Test
//    void validateCertificateWithInvalidDescriptionTest() {
//        testCertificate.setDescription("test");
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//
//    @Test
//    void validateCertificateWithNullDescriptionTest() {
//        testCertificate.setDescription(null);
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//
//    @Test
//    void validateCertificateWithInvalidPriceTest() {
//        testCertificate.setPrice(new BigDecimal("0"));
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//
//    @Test
//    void validateCertificateWithNullPriceTest() {
//        testCertificate.setPrice(null);
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//
//    @Test
//    void validateCertificateWithInvalidDurationTest() {
//        testCertificate.setDuration((short) 1);
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//
//    @Test
//    void validateCertificateWithInvalidCreateDateTest() {
//        testCertificate.setCreateDate(null);
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//
//    @Test
//    void validateCertificateWithInvalidLastUpdateDateTest() {
//        testCertificate.setLastUpdateDate(null);
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//
//    @Test
//    void validateCertificateWithInvalidTagsTest() {
//        testCertificate.setTags(null);
//        Assertions.assertThrows(InvalidCertificateException.class, () -> certificateValidator.validateCertificate(testCertificate));
//    }
//}