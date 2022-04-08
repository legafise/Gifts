package com.epam.esm.controller;

import com.epam.esm.MJCApplication;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.constant.MJCPaginationConstant;
import com.epam.esm.service.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MJCApplication.class)
@ActiveProfiles("hibernate-test")
@Transactional
class CertificateControllerHibernateTest {
    @Autowired
    private MJCCertificateController certificateController;
    private Certificate testCertificate;
    private EntityModel<CertificateDto> testCertificateModel;
    private List<EntityModel<CertificateDto>> testCertificateModelList;

    @BeforeEach
    void setUp() {
        Tag firstTestTag = new Tag(102, "Jumps");
        Tag secondTestTag = new Tag(103, "Entertainment");
        Tag thirdTestTag = new Tag(101, "Tattoo");
        TagDto firstTestTagDto = new TagDto(102, "Jumps");
        TagDto secondTestTagDto = new TagDto(103, "Entertainment");
        TagDto thirdTestTagDto = new TagDto(101, "Tattoo");

        testCertificate = new Certificate(101, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), new HashSet<>(Collections.singletonList(thirdTestTag)));
        CertificateDto testCertificateDto = new CertificateDto(101, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), Collections.singletonList(thirdTestTagDto));

        testCertificateModel = EntityModel.of(testCertificateDto);
        testCertificateModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCTagController.class).readTag(101)).withRel("Tag(Tattoo) information"));

        Certificate secondTestCertificate = new Certificate(102, "Jump park", "Free jumps at trampolines",
                new BigDecimal("35.00"), (short) 30, LocalDateTime.parse("2022-03-15T21:30"),
                LocalDateTime.parse("2022-06-15T21:30"), new HashSet<>(Arrays.asList(firstTestTag, secondTestTag)));
        CertificateDto secondTestCertificateDto = new CertificateDto(102, "Jump park", "Free jumps at trampolines",
                new BigDecimal("35.00"), (short) 30, LocalDateTime.parse("2022-03-15T21:30"),
                LocalDateTime.parse("2022-06-15T21:30"), Arrays.asList(firstTestTagDto, secondTestTagDto));
        EntityModel<CertificateDto> secondTestCertificateModel = EntityModel.of(secondTestCertificateDto);
        secondTestCertificateModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCTagController.class).readTag(102)).withRel("Tag(Jumps) information"));
        secondTestCertificateModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCTagController.class).readTag(103)).withRel("Tag(Entertainment) information"));

        testCertificateModelList = Arrays.asList(testCertificateModel, secondTestCertificateModel);
    }

    @Test
    void readCertificateByIdTest() {
        Assertions.assertEquals(testCertificateModel, certificateController.readCertificateById(101));
    }

    @Test
    void readCertificateWithInvalidIdTest() {
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> certificateController.readCertificateById(458));
    }

    @Test
    void readAllCertificatesTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");

        Assertions.assertEquals(testCertificateModelList, certificateController.readAllCertificates(pageParameters, new ArrayList<>()));
    }

    @Test
    void readAllCertificatesWithInvalidPageTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "-10");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");

        Assertions.assertThrows(MJCInvalidPaginationDataException.class, () -> certificateController.readAllCertificates(pageParameters, new ArrayList<>()));
    }

    @Test
    void readAllCertificatesWithInvalidPageSizeTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "-35");

        Assertions.assertThrows(MJCInvalidPaginationDataException.class, () -> certificateController.readAllCertificates(pageParameters, new ArrayList<>()));
    }

    @Test
    void readAllCertificatesWithoutPaginationParametersTest() {
        Assertions.assertThrows(MJCMissingPageNumberException.class, () -> certificateController.readAllCertificates(new HashMap<>(), new ArrayList<>()));
    }

    @Test
    void updateCertificateTest() {
        testCertificate.setName("SuperTattoo");
        EntityModel<CertificateDto> updatedCertificate = certificateController.updateCertificate(testCertificate, 101);
        Objects.requireNonNull(testCertificateModel.getContent()).setLastUpdateDate(Objects.requireNonNull(updatedCertificate.getContent()).getLastUpdateDate());
        testCertificateModel.getContent().setName("SuperTattoo");
        testCertificateModel.getContent().setTags(updatedCertificate.getContent().getTags());
        Assertions.assertEquals(testCertificateModel, updatedCertificate);
    }

    @Test
    void updateCertificateWithInvalidCertificateIdTest() {
        testCertificate.setName("SuperTattoo");
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> certificateController.updateCertificate(testCertificate, 294));
    }

    @Test
    void updateInvalidCertificateTest() {
        testCertificate.setName("");
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateController.updateCertificate(testCertificate, 101));
    }

    @Test
    void createInvalidCertificateTest() {
        testCertificate.setName("");
        Assertions.assertThrows(MJCInvalidEntityException.class, () -> certificateController.createCertificate(testCertificate));
    }

    @Test
    void createExistedCertificateTest() {
        Assertions.assertThrows(MJCEntityDuplicationException.class, () -> certificateController.createCertificate(testCertificate));
    }

    @Test
    void patchCertificateTest() {
        Certificate certificate = new Certificate();
        certificate.setName("NewCertificate");
        Objects.requireNonNull(testCertificateModel.getContent()).setName("NewCertificate");
        EntityModel<CertificateDto> patchedCertificateModel = certificateController.patchCertificate(certificate, 101);
        testCertificateModel.getContent().setLastUpdateDate(Objects.requireNonNull(patchedCertificateModel.getContent()).getLastUpdateDate());
        Assertions.assertEquals(testCertificateModel, patchedCertificateModel);
    }

    @Test
    void patchUnknownCertificateTest() {
        Certificate certificate = new Certificate();
        certificate.setName("NewCertificate");
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> certificateController.patchCertificate(certificate, 837));
    }

    @Test
    void patchDuplicateCertificateTest() {
        Certificate certificate = new Certificate();
        certificate.setName("Jump park");
        Assertions.assertThrows(MJCEntityDuplicationException.class, () -> certificateController.patchCertificate(certificate, 101));
    }

    @Test
    void deleteCertificateTest() {
        certificateController.deleteCertificate(101);
    }

    @Test
    void deleteUnknownCertificateTest() {
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> certificateController.deleteCertificate(3775));
    }
}